import { ConditionOperator, GreaterThanInclusiveOperator } from '../../../src';

describe('GreaterThanInclusiveOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new GreaterThanInclusiveOperator(1);

        expect(operator.operatorType).toEqual(ConditionOperator.GREATER_THAN_INCLUSIVE);
        expect(operator.valueToCompareAgainst).toEqual(1);
        expect(operator.evaluate(1)).toBeTruthy();
        expect(operator.evaluate(2)).toBeTruthy();
        expect(operator.evaluate(0)).toBeFalsy();
    });
});
