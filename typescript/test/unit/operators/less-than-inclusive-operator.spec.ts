import { ConditionOperator, LessThanInclusiveOperator } from '../../../src';

describe('LessThanInclusiveOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new LessThanInclusiveOperator(10);

        expect(operator.operatorType).toEqual(ConditionOperator.LESS_THAN_INCLUSIVE);
        expect(operator.valueToCompareAgainst).toEqual(10);
        expect(operator.evaluate(10)).toBeTruthy();
        expect(operator.evaluate(9)).toBeTruthy();
        expect(operator.evaluate(0)).toBeTruthy();
        expect(operator.evaluate(11)).toBeFalsy();
    });
});
