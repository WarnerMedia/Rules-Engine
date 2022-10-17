import { ConditionOperator, NonNullableJSONValue } from '../types/external';
import { AbstractOperator } from './abstract-operator';

export class ArrayContainsOperator extends AbstractOperator<
    NonNullableJSONValue[],
    NonNullableJSONValue
> {
    public constructor(valueToCompareAgainst: NonNullableJSONValue[]) {
        super(ConditionOperator.ARRAY_CONTAINS, valueToCompareAgainst);
    }

    public evaluate(factValue: NonNullableJSONValue): boolean {
        return this._valueToCompareAgainst.includes(factValue);
    }
}
