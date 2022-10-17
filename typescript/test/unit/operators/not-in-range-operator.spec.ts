import { ConditionOperator, NotInRangeOperator } from '../../../src';

describe('NotInRangeOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new NotInRangeOperator({ start: 100, end: 200 });

        expect(operator.operatorType).toEqual(ConditionOperator.NOT_IN_RANGE);
        expect(operator.valueToCompareAgainst).toEqual({ start: 100, end: 200 });
        expect(operator.evaluate(99)).toBeTruthy();
        expect(operator.evaluate(201)).toBeTruthy();
        expect(operator.evaluate(100)).toBeFalsy();
        expect(operator.evaluate(150)).toBeFalsy();
        expect(operator.evaluate(200)).toBeFalsy();
    });
});
