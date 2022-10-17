import { deepStrictEqual } from 'assert';
import { readFileSync, writeFileSync } from 'fs';

import { EngineDefinitionError, EngineEvaluationError } from '../errors';
import {
    IEvaluationResult,
    IMemoizedData,
    IRuleEngineOptions,
    IRuleResult,
    JSONValue,
    ResultType,
    RuleEngineEvaluationPolicy,
    RuleResult,
} from '../types/external';
import {
    CURRENT_JSON_REPRESENTATION_VERSION,
    IRuleEngine,
    IRuleEngineJsonRepresentation,
    IRuleJsonRepresentation,
    IRulesJsonRepresentation,
} from '../types/internal';
import { mapJsonToCondition } from '../utils';
import { Rule } from './rule';

export class RuleEngine implements IRuleEngine {
    public readonly name: string;
    public readonly rules: Rule[];
    public readonly options: IRuleEngineOptions;

    /**
     * Create a new instance of rule engine
     * @param name Name for the rule engine instance
     * @param rules List of rules for the engine instance
     * @param options Optional properties for rule engine instance
     */
    public constructor(name: string, rules: Rule[], options?: Partial<IRuleEngineOptions>) {
        this.name = name;
        this.rules = rules;
        this.options = {
            evaluationPolicy: options?.evaluationPolicy ?? RuleEngineEvaluationPolicy.EVALUATE_ALL,
            memoizeConditionResults: options?.memoizeConditionResults ?? false,
            memoizeRuleResults: options?.memoizeRuleResults ?? false,
            sortRulesByPriority: options?.sortRulesByPriority ?? false,
        };

        this.sortRulesByDecreasingPriority();
        this.sortConditionsByMemoizationPossibility();
        this.validateRuleEngine();
    }

    /**
     * Evaluate the facts against rules associated with the rule engine instance
     * @param facts Information to evaluate rule set against
     */
    public evaluate(facts: Record<string, JSONValue>): IEvaluationResult {
        const results: IRuleResult[] = [];
        const memoizedData: IMemoizedData = this.defaultMemoizedData();

        for (const rule of this.rules) {
            const result = rule.evaluate(facts, memoizedData, {
                memoizeConditionResult: this.options.memoizeConditionResults,
                memoizeRuleResult: this.options.memoizeRuleResults,
            });

            const ruleResult: IRuleResult = { name: rule.name, result };
            results.push(ruleResult);

            if (this.shortCircuitRuleEngineEvaluation(result)) {
                return this.createEvaluationResult(results, ruleResult);
            }
        }

        return this.createEvaluationResult(results);
    }

    /**
     * Get details about possible usage of memoization options
     */
    public getMemoizationDetails(): Record<string, unknown> {
        let possibleResultMemoizationCount = 0;
        let maxPossibleResultMemoizationMapSize = 0;
        let totalConditions = 0;
        const memoizedNames = new Set<string>();

        this.rules.forEach((rule) => {
            rule.conditions.forEach((condition) => {
                if (memoizedNames.has(condition.memoizedName)) {
                    possibleResultMemoizationCount += 1;
                } else {
                    maxPossibleResultMemoizationMapSize += condition.memoizedName.length + 1;
                    memoizedNames.add(condition.memoizedName);
                }
                totalConditions += 1;
            });
        });

        return {
            possibleConditionsWithMemoizedResult: `${possibleResultMemoizationCount}/${totalConditions}`,
            maxPossibleMemoizedResultMapSize: this.formatByteSize(
                maxPossibleResultMemoizationMapSize,
            ),
        };
    }

    /**
     * Save rule engine instance as JSON to a file
     * @param path Path to save the JSON representation to
     */
    public saveEngine(path: string): void {
        writeFileSync(path, JSON.stringify(this.defaultEngineRepresentation(), undefined, 2), {
            encoding: 'utf-8',
        });
    }

    /**
     * Save rules of the rule engine instance as JSON to a file
     * @param path Path to save the JSON representation to
     */
    public saveRules(path: string): void {
        writeFileSync(path, JSON.stringify(this.defaultRulesRepresentation(), undefined, 2), {
            encoding: 'utf-8',
        });
    }

    /**
     * Test the rule engine instance evaluation result against input facts
     * @param facts Information to evaluate the rule engine instance against
     * @param expectedResult Result to compare the evaluated output with
     */
    public testEngine(facts: Record<string, JSONValue>, expectedResult: IEvaluationResult): void {
        const evaluationResult = this.evaluate(facts);

        deepStrictEqual(evaluationResult, expectedResult);
    }

    /**
     * Test specific rule against input facts
     * @param ruleName Name of the already defined rule to test
     * @param facts Information to evaluate the rule against
     * @param expectedResult Result to compare the evaluated result with
     */
    public testRule(
        ruleName: string,
        facts: Record<string, JSONValue>,
        expectedResult: RuleResult,
    ): void {
        const rule = this.rules.find((r) => r.name === ruleName);

        if (!rule) {
            throw new EngineEvaluationError(`no rule found with name: ${ruleName}`);
        }

        const result = rule.evaluate(facts, this.defaultMemoizedData(), {
            memoizeConditionResult: false,
            memoizeRuleResult: false,
        });

        deepStrictEqual(result, expectedResult);
    }

    /**
     * Read JSON representation of a rule engine instance from disk
     * @param path Path to the saved json representation of the rule engine
     */
    public static readEngineFromDisk(path: string): RuleEngine {
        const file = readFileSync(path, { encoding: 'utf-8' });
        const json = JSON.parse(file);
        const jsonIsRulesEngine = this.objectIsRuleEngineJsonRepresentation(json);

        if (!jsonIsRulesEngine) {
            throw new EngineDefinitionError('wrong file contents');
        }

        switch (json.version) {
            case 1:
                return this.createRuleEngineFromJsonV1(json);
            default:
                throw new EngineDefinitionError(
                    `unsupported engine version number: ${json.version}`,
                );
        }
    }

    /**
     * Read JSON representation of rules array from disk
     * @param path Path to the saved json representation of the rules array
     */
    public static readRulesFromDisk(path: string): Rule[] {
        const file = readFileSync(path, { encoding: 'utf-8' });
        const json = JSON.parse(file);
        const jsonIsRulesArray = this.objectIsRulesJsonRepresentation(json);

        if (!jsonIsRulesArray) {
            throw new EngineDefinitionError('wrong file contents');
        }

        switch (json.version) {
            case 1:
                return this.createRulesArrayFromJsonV1(json.rules);
            default:
                throw new EngineDefinitionError(
                    `unsupported rules version number: ${json.version}`,
                );
        }
    }

    private defaultEngineRepresentation(): IRuleEngineJsonRepresentation {
        return {
            name: this.name,
            rules: this.defaultRulesRepresentation().rules,
            options: this.options,
            version: CURRENT_JSON_REPRESENTATION_VERSION,
        };
    }

    private defaultRulesRepresentation(): IRulesJsonRepresentation {
        return {
            rules: this.rules.map((r) => ({
                name: r.name,
                result: r.result,
                options: r.options,
                conditions: r.conditions.map((c) => ({
                    fact: c.fact,
                    operator: {
                        operatorType: c.operator.operatorType,
                        valueToCompareAgainst: c.operator.valueToCompareAgainst,
                    },
                    options: c.options,
                })),
            })),
            version: CURRENT_JSON_REPRESENTATION_VERSION,
        };
    }

    private defaultMemoizedData(): IMemoizedData {
        return {
            conditionResults: {},
        };
    }

    private createEvaluationResult(
        ruleEvaluations: IRuleResult[],
        exitConditionResult?: IRuleResult,
    ): IEvaluationResult {
        return exitConditionResult
            ? {
                  ruleEvaluations,
                  exitCriteria: { earlyExit: true, rule: exitConditionResult },
              }
            : { ruleEvaluations, exitCriteria: { earlyExit: false } };
    }

    /* istanbul ignore next */
    private formatByteSize(bytes: number): string {
        if (bytes < 1024) {
            return bytes + ' bytes';
        } else if (bytes < 1048576) {
            return (bytes / 1024).toFixed(3) + ' KiB';
        } else {
            return (bytes / 1048576).toFixed(3) + ' MiB';
        }
    }

    private shortCircuitRuleEngineEvaluation(result: RuleResult): boolean {
        if (this.options.evaluationPolicy === RuleEngineEvaluationPolicy.EVALUATE_ALL) {
            return false;
        }

        if (
            result.type === ResultType.SUCCESS &&
            this.options.evaluationPolicy === RuleEngineEvaluationPolicy.EVALUATE_TO_FIRST_SUCCESS
        ) {
            return true;
        }

        if (
            result.type === ResultType.FAILURE &&
            this.options.evaluationPolicy === RuleEngineEvaluationPolicy.EVALUATE_TO_FIRST_FAILURE
        ) {
            return true;
        }

        return false;
    }

    private sortRulesByDecreasingPriority(): void {
        if (!this.options.sortRulesByPriority) {
            return;
        }

        this.rules.sort((ruleOne, ruleTwo) => ruleTwo.options.priority - ruleOne.options.priority);
    }

    private sortConditionsByMemoizationPossibility(): void {
        if (!this.options.memoizeConditionResults) {
            return;
        }

        const memoizedNames = new Set<string>();

        this.rules.forEach((rule) => {
            rule.conditions.sort((conditionOne, conditionTwo) => {
                const conditionOneIsMemoized = memoizedNames.has(conditionOne.memoizedName);
                const conditionTwoIsMemoized = memoizedNames.has(conditionTwo.memoizedName);

                return conditionOneIsMemoized && conditionTwoIsMemoized
                    ? 0
                    : conditionOneIsMemoized
                    ? -1
                    : 1;
            });
            rule.conditions.forEach((condition) => memoizedNames.add(condition.memoizedName));
        });
    }

    private validateRuleEngine(): void {
        const ruleNames = new Set<string>();

        this.rules.forEach((r) => {
            if (ruleNames.has(r.name)) {
                throw new EngineDefinitionError(`two rules found with the same name: ${r.name}`);
            }
            if (r.options.priority < 0 || r.options.priority > Number.MAX_SAFE_INTEGER) {
                throw new EngineDefinitionError(`rule priority out of bounds for rule: ${r.name}`);
            }
            ruleNames.add(r.name);
        });
    }

    private static createRuleEngineFromJsonV1(json: IRuleEngineJsonRepresentation): RuleEngine {
        const parsedName = json.name;
        const parsedOptions = json.options;

        const parsedRules = json.rules;
        const createdRules = this.createRulesArrayFromJsonV1(parsedRules);

        return new RuleEngine(parsedName, createdRules, parsedOptions);
    }

    private static createRulesArrayFromJsonV1(json: IRuleJsonRepresentation[]): Rule[] {
        return json.map(
            (parsedRule: IRuleJsonRepresentation) =>
                new Rule(
                    parsedRule.name,
                    parsedRule.conditions.map(mapJsonToCondition),
                    parsedRule.result,
                    parsedRule.options,
                ),
        );
    }

    private static objectIsRulesJsonRepresentation(
        parsedJson: unknown,
    ): parsedJson is IRulesJsonRepresentation {
        /* istanbul ignore next */
        if (typeof parsedJson !== 'object') {
            return false;
        }

        if (!('rules' in parsedJson!)) {
            return false;
        }

        if (!('version' in parsedJson)) {
            return false;
        }

        // @ts-ignore: rules will exist
        const rules = parsedJson.rules;

        if (!Array.isArray(rules)) {
            return false;
        }

        for (const parsedJsonElement of rules) {
            if (!('name' in parsedJsonElement)) {
                return false;
            }

            if (!('conditions' in parsedJsonElement)) {
                return false;
            }

            if (!('result' in parsedJsonElement)) {
                return false;
            }

            if (!('options' in parsedJsonElement)) {
                return false;
            }
        }

        return true;
    }

    private static objectIsRuleEngineJsonRepresentation(
        parsedJson: unknown,
    ): parsedJson is IRuleEngineJsonRepresentation {
        /* istanbul ignore next */
        if (typeof parsedJson !== 'object') {
            return false;
        }

        if (!('name' in parsedJson!)) {
            return false;
        }

        if (!('options' in parsedJson)) {
            return false;
        }

        if (!('rules' in parsedJson)) {
            return false;
        }

        if (!('version' in parsedJson)) {
            return false;
        }

        return true;
    }
}
