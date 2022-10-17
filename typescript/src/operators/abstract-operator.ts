import { ConditionOperator, NonNullableJSONValue } from '../types/external';
import { IConditionOperator } from '../types/internal';

export abstract class AbstractOperator<
    V extends NonNullableJSONValue,
    F extends NonNullableJSONValue = V,
> implements IConditionOperator
{
    protected readonly _operatorType: ConditionOperator;
    protected readonly _valueToCompareAgainst: V;

    protected constructor(operatorType: ConditionOperator, valueToCompareAgainst: V) {
        this._operatorType = operatorType;
        this._valueToCompareAgainst = valueToCompareAgainst;
    }

    public get operatorType(): ConditionOperator {
        return this._operatorType;
    }

    public get valueToCompareAgainst(): V {
        return this._valueToCompareAgainst;
    }

    public abstract evaluate(factValue: F): boolean;
}
