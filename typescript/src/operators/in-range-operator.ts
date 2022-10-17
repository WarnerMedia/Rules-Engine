import { ConditionOperator } from '../types/external';
import { IRange, RangedValueType } from '../types/internal';
import { AbstractOperator } from './abstract-operator';

export class InRangeOperator extends AbstractOperator<IRange, RangedValueType> {
    public constructor(valueToCompareAgainst: IRange) {
        super(ConditionOperator.IN_RANGE, valueToCompareAgainst);
    }

    public evaluate(factValue: RangedValueType): boolean {
        return (
            factValue >= this._valueToCompareAgainst.start &&
            factValue <= this._valueToCompareAgainst.end
        );
    }
}
