import {
    ConditionOperator,
    IConditionEvaluationOptions,
    IConditionOptions,
    IEvaluationResult,
    IMemoizedData,
    IRuleEngineOptions,
    IRuleEvaluationOptions,
    IRuleOptions,
    JSONValue,
    NonNullableJSONValue,
    RuleResult,
} from './external';

export const CURRENT_JSON_REPRESENTATION_VERSION = 1;

export const DEFAULT_RULE_END_TIMESTAMP = Number.MAX_SAFE_INTEGER;

export const DEFAULT_RULE_PRIORITY = 0;

export const DEFAULT_RULE_START_TIMESTAMP = 0;

export interface IRule {
    name: string;
    conditions: ICondition[];
    result: [NonNullableJSONValue, NonNullableJSONValue];
    options: IRuleOptions;

    evaluate(
        facts: Record<string, JSONValue>,
        memoizedData: IMemoizedData,
        options: IRuleEvaluationOptions,
    ): RuleResult;
}

export interface IRuleEngine {
    name: string;
    rules: IRule[];
    options: IRuleEngineOptions;

    evaluate(facts: Record<string, JSONValue>): IEvaluationResult;
}

export interface IRuleEngineJsonRepresentation extends Omit<IRuleEngine, 'evaluate' | 'rules'> {
    rules: IRuleJsonRepresentation[];
    version: number;
}

export interface IRuleJsonRepresentation extends Omit<IRule, 'evaluate' | 'conditions'> {
    conditions: IConditionJsonRepresentation[];
}

export interface IRulesJsonRepresentation {
    rules: IRuleJsonRepresentation[];
    version: number;
}

export interface ICondition extends IConditionJson {
    memoizedName: string;

    evaluate(
        facts: Record<string, JSONValue>,
        memoizedData: IMemoizedData,
        options: IConditionEvaluationOptions,
    ): boolean;
}

export interface IConditionJson {
    fact: string;
    operator: IConditionOperator;
    options: IConditionOptions;
}

export interface IConditionJsonRepresentation
    extends Omit<ICondition, 'evaluate' | 'operator' | 'memoizedName'> {
    operator: ConditionOperatorJsonRepresentation;
}

export interface IConditionOperator {
    operatorType: ConditionOperator;
    valueToCompareAgainst: NonNullableJSONValue;

    evaluate(factValue: NonNullableJSONValue): boolean;
}

export interface IRange<T = RangedValueType> extends Record<string, T> {
    start: T;
    end: T;
}

export type ConditionOperatorJsonRepresentation = Omit<IConditionOperator, 'evaluate'>;

export type ExtractElementType<T extends Readonly<string[]>> = T extends Readonly<Array<infer U>>
    ? U
    : never;

export type RangedValueType = string | number;
