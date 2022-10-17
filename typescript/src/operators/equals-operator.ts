import { ConditionOperator, NonNullableJSONValue } from '../types/external';
import { AbstractOperator } from './abstract-operator';

export class EqualsOperator extends AbstractOperator<NonNullableJSONValue, NonNullableJSONValue> {
    public constructor(valueToCompareAgainst: NonNullableJSONValue) {
        super(ConditionOperator.EQUALS, valueToCompareAgainst);
    }

    public evaluate(factValue: NonNullableJSONValue): boolean {
        return factValue === this._valueToCompareAgainst;
    }
}
