import { EngineDefinitionError } from '../errors';
import {
    IMemoizedData,
    IRuleEvaluationOptions,
    IRuleOptions,
    JSONValue,
    NonNullableJSONValue,
    RuleResult,
} from '../types/external';
import {
    DEFAULT_RULE_END_TIMESTAMP,
    DEFAULT_RULE_PRIORITY,
    DEFAULT_RULE_START_TIMESTAMP,
    ICondition,
    IRule,
} from '../types/internal';
import {
    createFailureResult,
    createSkippedResult,
    createSuccessResult,
    getCurrentEpochTime,
} from '../utils';

export class Rule implements IRule {
    public readonly name: string;
    public readonly conditions: ICondition[];
    public readonly options: IRuleOptions;
    public readonly result: [NonNullableJSONValue, NonNullableJSONValue];
    private readonly successResult: NonNullableJSONValue;
    private readonly failureResult: NonNullableJSONValue;

    /**
     * Create a new rule
     * @param name Name of the rule
     * @param conditions List of conditions for the rule
     * @param result Tuple for rule output in case of successful and
     * failure evaluation respectively
     * @param options Optional properties for the rule
     */
    public constructor(
        name: string,
        conditions: ICondition[],
        result?: [NonNullableJSONValue?, NonNullableJSONValue?],
        options?: Partial<IRuleOptions>,
    ) {
        this.name = name;
        this.conditions = conditions;
        this.successResult = result?.[0] ?? true;
        this.failureResult = result?.[1] ?? false;
        this.result = [this.successResult, this.failureResult];
        this.options = {
            disabled: options?.disabled ?? false,
            endTimestamp: options?.endTimestamp ?? DEFAULT_RULE_END_TIMESTAMP,
            negateEvaluation: options?.negateEvaluation ?? false,
            priority: options?.priority ?? DEFAULT_RULE_PRIORITY,
            startTimestamp: options?.startTimestamp ?? DEFAULT_RULE_START_TIMESTAMP,
        };

        this.validateRule();
    }

    /**
     * Evaluate the facts against conditions associated with the rule
     * @param facts Information to evaluate the rule against
     * @param memoizedData Memoized data to use during rule evaluation
     * @param options Options to use for rule evaluation
     */
    public evaluate(
        facts: Record<string, JSONValue>,
        memoizedData: IMemoizedData,
        options: IRuleEvaluationOptions,
    ): RuleResult {
        if (this.skipEvaluation()) {
            return createSkippedResult();
        }

        const conditionsEvalResult = this.evaluateConditions(facts, memoizedData, options);

        if (options.memoizeRuleResult) {
            facts[this.name] = conditionsEvalResult;
        }

        return conditionsEvalResult
            ? createSuccessResult(this.successResult)
            : createFailureResult(this.failureResult);
    }

    private evaluateConditions(
        facts: Record<string, JSONValue>,
        memoizedData: IMemoizedData,
        options: IRuleEvaluationOptions,
    ): boolean {
        const evaluation = this.conditions.every((condition) =>
            condition.evaluate(facts, memoizedData, {
                memoizeConditionResult: options.memoizeConditionResult,
            }),
        );
        return this.options.negateEvaluation ? !evaluation : evaluation;
    }

    private skipEvaluation(): boolean {
        if (this.options.disabled) {
            return true;
        }

        if (
            this.options.startTimestamp === DEFAULT_RULE_START_TIMESTAMP &&
            this.options.endTimestamp === DEFAULT_RULE_END_TIMESTAMP
        ) {
            return false;
        }

        const currentTime = getCurrentEpochTime();
        return currentTime < this.options.startTimestamp || currentTime > this.options.endTimestamp;
    }

    private validateRule(): void {
        if (
            this.options.startTimestamp < DEFAULT_RULE_START_TIMESTAMP ||
            this.options.endTimestamp > DEFAULT_RULE_END_TIMESTAMP ||
            this.options.startTimestamp > this.options.endTimestamp
        ) {
            throw new EngineDefinitionError(`incorrect timestamp for rule: ${this.name}`);
        }
    }
}
