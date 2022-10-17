import { Condition } from './engine';
import { EngineDefinitionError } from './errors';
import {
    ArrayContainsOperator,
    EqualsOperator,
    GreaterThanInclusiveOperator,
    GreaterThanOperator,
    InRangeOperator,
    IsDefinedOperator,
    LessThanInclusiveOperator,
    LessThanOperator,
    NotArrayContainsOperator,
    NotEqualsOperator,
    NotInRangeOperator,
    StringContainsOperator,
} from './operators';
import { AbstractOperator } from './operators/abstract-operator';
import {
    ConditionOperator,
    JSONValue,
    NonNullableJSONValue,
    ResultType,
    RuleResult,
} from './types/external';
import { IConditionJsonRepresentation, IRange, RangedValueType } from './types/internal';

type OPERATOR_MAP_RETURN_TYPE = {
    [operator in ConditionOperator]: (
        v: NonNullableJSONValue,
    ) => AbstractOperator<NonNullableJSONValue>;
};

const OPERATOR_MAP: OPERATOR_MAP_RETURN_TYPE = {
    [ConditionOperator.ARRAY_CONTAINS]: (v: NonNullableJSONValue) => {
        return new ArrayContainsOperator(v as NonNullableJSONValue[]);
    },
    [ConditionOperator.EQUALS]: (v: NonNullableJSONValue) => {
        return new EqualsOperator(v);
    },
    [ConditionOperator.GREATER_THAN]: (v: NonNullableJSONValue) => {
        return new GreaterThanOperator(v as RangedValueType);
    },
    [ConditionOperator.GREATER_THAN_INCLUSIVE]: (v: NonNullableJSONValue) => {
        return new GreaterThanInclusiveOperator(v as RangedValueType);
    },
    [ConditionOperator.IN_RANGE]: (v: NonNullableJSONValue) => {
        return new InRangeOperator(v as IRange);
    },
    [ConditionOperator.IS_DEFINED]: (v: NonNullableJSONValue) => {
        return new IsDefinedOperator(v as never);
    },
    [ConditionOperator.LESS_THAN]: (v: NonNullableJSONValue) => {
        return new LessThanOperator(v as RangedValueType);
    },
    [ConditionOperator.LESS_THAN_INCLUSIVE]: (v: NonNullableJSONValue) => {
        return new LessThanInclusiveOperator(v as RangedValueType);
    },
    [ConditionOperator.NOT_ARRAY_CONTAINS]: (v: NonNullableJSONValue) => {
        return new NotArrayContainsOperator(v as NonNullableJSONValue[]);
    },
    [ConditionOperator.NOT_EQUALS]: (v: NonNullableJSONValue) => {
        return new NotEqualsOperator(v);
    },
    [ConditionOperator.NOT_IN_RANGE]: (v: NonNullableJSONValue) => {
        return new NotInRangeOperator(v as IRange);
    },
    [ConditionOperator.STRING_CONTAINS]: (v: NonNullableJSONValue) => {
        return new StringContainsOperator(v as string);
    },
};

export function createSuccessResult(value: NonNullableJSONValue): RuleResult {
    return {
        type: ResultType.SUCCESS,
        value,
    };
}

export function createFailureResult(value: NonNullableJSONValue): RuleResult {
    return {
        type: ResultType.FAILURE,
        value,
    };
}

export function createSkippedResult(): RuleResult {
    return {
        type: ResultType.SKIPPED,
    };
}

export function getCurrentEpochTime(): number {
    return Math.floor(Date.now() / 1000);
}

export function getJsonPathElement(path: string, facts: Record<string, JSONValue>): JSONValue {
    return facts[path as string];
}

export function mapJsonToCondition(
    parsedCondition: IConditionJsonRepresentation,
): Condition<string[]> {
    const operatorType = OPERATOR_MAP[parsedCondition.operator.operatorType];
    if (!operatorType) {
        throw new EngineDefinitionError(
            `operator not supported: ${parsedCondition.operator.operatorType}`,
        );
    }
    return new Condition(
        parsedCondition.fact,
        OPERATOR_MAP[parsedCondition.operator.operatorType](
            parsedCondition.operator.valueToCompareAgainst,
        ),
        parsedCondition.options,
    );
}
