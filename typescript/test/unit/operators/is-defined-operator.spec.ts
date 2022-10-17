import { ConditionOperator, IsDefinedOperator } from '../../../src';

describe('IsDefinedOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new IsDefinedOperator({} as never);

        expect(operator.operatorType).toEqual(ConditionOperator.IS_DEFINED);
        expect(operator.valueToCompareAgainst).toEqual({});
        expect(operator.evaluate(1)).toBeTruthy();
        expect(operator.evaluate(2)).toBeTruthy();
        expect(operator.evaluate('')).toBeTruthy();
    });
});
