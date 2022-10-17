import { ConditionOperator, NonNullableJSONValue } from '../types/external';
import { AbstractOperator } from './abstract-operator';

export class IsDefinedOperator extends AbstractOperator<NonNullableJSONValue, never> {
    public constructor(valueToCompareAgainst: never) {
        super(ConditionOperator.IS_DEFINED, valueToCompareAgainst);
    }

    public evaluate(factValue: NonNullableJSONValue): boolean {
        return factValue !== undefined;
    }
}
