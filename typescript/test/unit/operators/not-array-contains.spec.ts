import { ConditionOperator, NotArrayContainsOperator } from '../../../src';

describe('NotArrayContainsOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new NotArrayContainsOperator([1, 2, 3]);

        expect(operator.operatorType).toEqual(ConditionOperator.NOT_ARRAY_CONTAINS);
        expect(operator.valueToCompareAgainst).toEqual([1, 2, 3]);
        expect(operator.evaluate(0)).toBeTruthy();
        expect(operator.evaluate(4)).toBeTruthy();
        expect(operator.evaluate(1)).toBeFalsy();
        expect(operator.evaluate(2)).toBeFalsy();
    });
});
