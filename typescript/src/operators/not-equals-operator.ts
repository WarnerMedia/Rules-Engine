import { ConditionOperator, NonNullableJSONValue } from '../types/external';
import { AbstractOperator } from './abstract-operator';

export class NotEqualsOperator extends AbstractOperator<
    NonNullableJSONValue,
    NonNullableJSONValue
> {
    public constructor(valueToCompareAgainst: NonNullableJSONValue) {
        super(ConditionOperator.NOT_EQUALS, valueToCompareAgainst);
    }

    public evaluate(factValue: NonNullableJSONValue): boolean {
        return factValue !== this._valueToCompareAgainst;
    }
}
