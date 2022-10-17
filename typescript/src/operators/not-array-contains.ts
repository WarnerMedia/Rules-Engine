import { ConditionOperator, NonNullableJSONValue } from '../types/external';
import { AbstractOperator } from './abstract-operator';

export class NotArrayContainsOperator extends AbstractOperator<
    NonNullableJSONValue[],
    NonNullableJSONValue
> {
    public constructor(valueToCompareAgainst: NonNullableJSONValue[]) {
        super(ConditionOperator.NOT_ARRAY_CONTAINS, valueToCompareAgainst);
    }

    public evaluate(factValue: NonNullableJSONValue): boolean {
        return !this._valueToCompareAgainst.includes(factValue);
    }
}
