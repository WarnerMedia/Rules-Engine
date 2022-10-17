import { ArrayContainsOperator, ConditionOperator } from '../../../src';

describe('ArrayContainsOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new ArrayContainsOperator([1, 2, 3]);

        expect(operator.operatorType).toEqual(ConditionOperator.ARRAY_CONTAINS);
        expect(operator.valueToCompareAgainst).toEqual([1, 2, 3]);
        expect(operator.evaluate(1)).toBeTruthy();
        expect(operator.evaluate(2)).toBeTruthy();
        expect(operator.evaluate(3)).toBeTruthy();
        expect(operator.evaluate(4)).toBeFalsy();
    });
});
