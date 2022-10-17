import { ConditionOperator } from '../types/external';
import { AbstractOperator } from './abstract-operator';

export class StringContainsOperator extends AbstractOperator<string, string> {
    public constructor(valueToCompareAgainst: string) {
        super(ConditionOperator.STRING_CONTAINS, valueToCompareAgainst);
    }

    public evaluate(factValue: string): boolean {
        return factValue.includes(this._valueToCompareAgainst);
    }
}
