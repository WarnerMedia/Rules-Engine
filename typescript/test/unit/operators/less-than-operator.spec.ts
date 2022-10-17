import { ConditionOperator, LessThanOperator } from '../../../src';

describe('LessThanOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new LessThanOperator(10);

        expect(operator.operatorType).toEqual(ConditionOperator.LESS_THAN);
        expect(operator.valueToCompareAgainst).toEqual(10);
        expect(operator.evaluate(10)).toBeFalsy();
        expect(operator.evaluate(9)).toBeTruthy();
        expect(operator.evaluate(0)).toBeTruthy();
        expect(operator.evaluate(11)).toBeFalsy();
    });
});
