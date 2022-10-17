import { ConditionOperator, EqualsOperator } from '../../../src';

describe('EqualsOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new EqualsOperator(1);

        expect(operator.operatorType).toEqual(ConditionOperator.EQUALS);
        expect(operator.valueToCompareAgainst).toEqual(1);
        expect(operator.evaluate(1)).toBeTruthy();
        expect(operator.evaluate(2)).toBeFalsy();
    });
});
