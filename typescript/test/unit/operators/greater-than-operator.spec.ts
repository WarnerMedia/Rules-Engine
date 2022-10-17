import { ConditionOperator, GreaterThanOperator } from '../../../src';

describe('GreaterThanOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new GreaterThanOperator(1);

        expect(operator.operatorType).toEqual(ConditionOperator.GREATER_THAN);
        expect(operator.valueToCompareAgainst).toEqual(1);
        expect(operator.evaluate(0)).toBeFalsy();
        expect(operator.evaluate(1)).toBeFalsy();
        expect(operator.evaluate(2)).toBeTruthy();
    });
});
