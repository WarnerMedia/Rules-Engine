export const IGNORE_UNDEFINED_AS_NOT_APPLICABLE = 'IGNORE_UNDEFINED_AS_NOT_APPLICABLE';

export const enum ConditionOperator {
    ARRAY_CONTAINS = 'ARRAY_CONTAINS',
    EQUALS = 'EQUALS',
    GREATER_THAN = 'GREATER_THAN',
    GREATER_THAN_INCLUSIVE = 'GREATER_THAN_INCLUSIVE',
    IN_RANGE = 'IN_RANGE',
    IS_DEFINED = 'IS_DEFINED',
    LESS_THAN = 'LESS_THAN',
    LESS_THAN_INCLUSIVE = 'LESS_THAN_INCLUSIVE',
    NOT_ARRAY_CONTAINS = 'NOT_ARRAY_CONTAINS',
    NOT_EQUALS = 'NOT_EQUALS',
    NOT_IN_RANGE = 'NOT_IN_RANGE',
    STRING_CONTAINS = 'STRING_CONTAINS',
}

export const enum ErrorType {
    ENGINE_DEFINITION = 'EngineDefinitionError',
    ENGINE_EVALUATION = 'EngineEvaluationError',
}

export const enum ResultType {
    FAILURE = 'FAILURE',
    SKIPPED = 'SKIPPED',
    SUCCESS = 'SUCCESS',
}

export const enum RuleEngineEvaluationPolicy {
    EVALUATE_ALL = 'EVALUATE_ALL',
    EVALUATE_TO_FIRST_SUCCESS = 'EVALUATE_TO_FIRST_SUCCESS',
    EVALUATE_TO_FIRST_FAILURE = 'EVALUATE_TO_FIRST_FAILURE',
}

export interface IConditionEvaluationOptions {
    memoizeConditionResult: boolean;
}

export interface IConditionOptions {
    ignoreUndefinedAs: boolean | typeof IGNORE_UNDEFINED_AS_NOT_APPLICABLE;
    useSHA1ForMemoizedName: boolean;
}

export interface IEvaluationResult {
    ruleEvaluations: IRuleResult[];
    exitCriteria: { earlyExit: false } | { earlyExit: true; rule: IRuleResult };
}

export interface IMemoizedData {
    conditionResults: Record<string, boolean>;
}

export interface IRuleOptions {
    disabled: boolean;
    /**
     * End timestamp for rule in epoch seconds
     * Defaults to `Number.MAX_SAFE_INTEGER`
     */
    endTimestamp: number;
    negateEvaluation: boolean;
    priority: number;
    /**
     * Start timestamp for rule in epoch seconds
     * Defaults to `0`
     */
    startTimestamp: number;
}

export interface IRuleEngineOptions {
    evaluationPolicy: RuleEngineEvaluationPolicy;
    memoizeConditionResults: boolean;
    memoizeRuleResults: boolean;
    sortRulesByPriority: boolean;
}

export interface IRuleEvaluationOptions extends IConditionEvaluationOptions {
    memoizeRuleResult: boolean;
}

export interface IRuleResult {
    name: string;
    result: RuleResult;
}

export type JSONValue =
    | string
    | number
    | boolean
    | { [_: string]: JSONValue }
    | JSONValue[]
    | undefined;

export type NonNullableJSONValue = NonNullable<JSONValue>;

export type RuleResult =
    | { type: ResultType.SUCCESS; value: NonNullableJSONValue }
    | { type: ResultType.FAILURE; value: NonNullableJSONValue }
    | { type: ResultType.SKIPPED };
