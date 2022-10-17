import { ConditionOperator, StringContainsOperator } from '../../../src';

describe('StringContainsOperatorArrayContainsOperator', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create operator instance and evaluate correctly', () => {
        const operator = new StringContainsOperator('test-string');

        expect(operator.operatorType).toEqual(ConditionOperator.STRING_CONTAINS);
        expect(operator.valueToCompareAgainst).toEqual('test-string');
        expect(operator.evaluate('test-string')).toBeTruthy();
        expect(operator.evaluate('abc-test-string-def')).toBeTruthy();
        expect(operator.evaluate('')).toBeFalsy();
        expect(operator.evaluate('test')).toBeFalsy();
    });
});
