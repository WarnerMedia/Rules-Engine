import { ConditionOperator, NotEqualsOperator } from '../../../src';

describe('NotEqualsOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new NotEqualsOperator(1);

        expect(operator.operatorType).toEqual(ConditionOperator.NOT_EQUALS);
        expect(operator.valueToCompareAgainst).toEqual(1);
        expect(operator.evaluate(2)).toBeTruthy();
        expect(operator.evaluate(1)).toBeFalsy();
    });
});
