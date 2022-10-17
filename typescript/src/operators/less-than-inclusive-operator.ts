import { ConditionOperator } from '../types/external';
import { RangedValueType } from '../types/internal';
import { AbstractOperator } from './abstract-operator';

export class LessThanInclusiveOperator extends AbstractOperator<RangedValueType, RangedValueType> {
    public constructor(valueToCompareAgainst: RangedValueType) {
        super(ConditionOperator.LESS_THAN_INCLUSIVE, valueToCompareAgainst);
    }

    public evaluate(factValue: RangedValueType): boolean {
        return factValue <= this._valueToCompareAgainst;
    }
}
