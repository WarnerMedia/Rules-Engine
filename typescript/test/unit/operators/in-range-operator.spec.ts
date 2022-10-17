import { ConditionOperator, InRangeOperator } from '../../../src';

describe('InRangeOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new InRangeOperator({ start: 100, end: 200 });

        expect(operator.operatorType).toEqual(ConditionOperator.IN_RANGE);
        expect(operator.valueToCompareAgainst).toEqual({ start: 100, end: 200 });
        expect(operator.evaluate(99)).toBeFalsy();
        expect(operator.evaluate(201)).toBeFalsy();
        expect(operator.evaluate(100)).toBeTruthy();
        expect(operator.evaluate(150)).toBeTruthy();
        expect(operator.evaluate(200)).toBeTruthy();
    });
});
