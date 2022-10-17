import { Condition, EqualsOperator } from '../../../src';

describe('Condition', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create condition instance correctly w/ default options', () => {
        const condition = new Condition('test-fact', new EqualsOperator(3));

        expect(condition.fact).toEqual('test-fact');
        expect(condition.operator).toBeDefined();
        expect(condition.options).toEqual({
            ignoreUndefinedAs: 'IGNORE_UNDEFINED_AS_NOT_APPLICABLE',
            useSHA1ForMemoizedName: false,
        });
        expect(condition.memoizedName).toBeDefined();
    });

    test('should create condition instance correctly w/o default options', () => {
        const condition = new Condition('test-fact', new EqualsOperator(3), {
            ignoreUndefinedAs: true,
            useSHA1ForMemoizedName: true,
        });

        expect(condition.fact).toEqual('test-fact');
        expect(condition.operator).toBeDefined();
        expect(condition.options).toEqual({
            ignoreUndefinedAs: true,
            useSHA1ForMemoizedName: true,
        });
        expect(condition.memoizedName).toBeDefined();
    });

    test('should create condition and evaluate correctly w/o memoization', () => {
        const condition = new Condition('factA', new EqualsOperator('abcd'));

        expect(
            condition.evaluate(
                { factA: 'abcd', factB: 100 },
                { conditionResults: {} },
                { memoizeConditionResult: false },
            ),
        ).toEqual(true);
    });

    test('should create condition and evaluate correctly w/ result memoization', () => {
        const condition = new Condition('factA', new EqualsOperator('abcd'));

        expect(
            condition.evaluate(
                { factA: 'abcd', factB: 100 },
                { conditionResults: {} },
                { memoizeConditionResult: true },
            ),
        ).toEqual(true);
    });

    test('should create condition and evaluate correctly w/ result memoization and use memoized result', () => {
        const condition = new Condition('factA', new EqualsOperator('abcd'));
        const memoizedName = condition.memoizedName;

        expect(
            condition.evaluate(
                { factA: 'abcd', factB: 100 },
                { conditionResults: { [memoizedName]: false } },
                { memoizeConditionResult: true },
            ),
        ).toEqual(false);
    });

    test('should create condition and evaluate if undefined w/ ignoreUndefined', () => {
        const condition = new Condition('factC', new EqualsOperator('abcd'), {
            ignoreUndefinedAs: true,
        });

        expect(
            condition.evaluate(
                { factA: 'abcd', factB: 100 },
                { conditionResults: {} },
                { memoizeConditionResult: true },
            ),
        ).toEqual(true);
    });
});
