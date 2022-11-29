import { Condition, EqualsOperator, GreaterThanInclusiveOperator, Rule } from '../../../src';

describe('Rule', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should create rule instance and getters w/ default options', () => {
        const rule = new Rule('test-rule', []);
        expect(rule.name).toEqual('test-rule');
        expect(rule.result).toEqual([true, false]);
        expect(rule.conditions).toHaveLength(0);
        expect(rule.options).toEqual({
            priority: 0,
            disabled: false,
            negateEvaluation: false,
            endTimestamp: 9007199254740991,
            startTimestamp: 0,
        });
    });

    test('should create rule instance and getters w/o default options', () => {
        const rule = new Rule('test-rule', [], [true, false], {
            priority: 10,
            disabled: true,
            negateEvaluation: true,
            startTimestamp: 100,
            endTimestamp: 200,
        });
        expect(rule.name).toEqual('test-rule');
        expect(rule.result).toEqual([true, false]);
        expect(rule.conditions).toHaveLength(0);
        expect(rule.options).toEqual({
            priority: 10,
            disabled: true,
            negateEvaluation: true,
            endTimestamp: 200,
            startTimestamp: 100,
        });
    });

    test('should error for rule w/ wrong options', () => {
        const ruleDefinition = () => new Rule('test-rule', [], [], { startTimestamp: -1 });

        expect(ruleDefinition).toThrow('incorrect timestamp for rule: test-rule');
    });

    test('should skip evaluation for disabled rule', () => {
        const rule = new Rule(
            'test-rule',
            [
                new Condition('factA', new EqualsOperator('abcd')),
                new Condition('factB', new GreaterThanInclusiveOperator(5)),
            ],
            [true, false],
            { disabled: true },
        );

        expect(
            rule.evaluate(
                { factA: 'abcde', factB: 100 },
                { conditionResults: {} },
                {
                    memoizeRuleResult: false,
                    memoizeConditionResult: false,
                },
            ),
        ).toEqual({ type: 'SKIPPED' });
    });

    test('should skip evaluation for inactive rule', () => {
        const rule = new Rule(
            'test-rule',
            [
                new Condition('factA', new EqualsOperator('abcd')),
                new Condition('factB', new GreaterThanInclusiveOperator(5)),
            ],
            [true, false],
            { disabled: false, startTimestamp: 10, endTimestamp: 20 },
        );

        expect(
            rule.evaluate(
                { factA: 'abcde', factB: 100 },
                { conditionResults: {} },
                {
                    memoizeRuleResult: false,
                    memoizeConditionResult: false,
                },
            ),
        ).toEqual({ type: 'SKIPPED' });
    });

    test('should evaluate conditions for the rule correctly to false', () => {
        const rule = new Rule(
            'test-rule',
            [
                new Condition('factA', new EqualsOperator('abcd')),
                new Condition('factB', new GreaterThanInclusiveOperator(5)),
            ],
            [true, false],
        );

        expect(
            rule.evaluate(
                { factA: 'abcde', factB: 100 },
                { conditionResults: {} },
                {
                    memoizeRuleResult: false,
                    memoizeConditionResult: false,
                },
            ),
        ).toEqual({ type: 'FAILURE', value: false });
    });

    test('should evaluate conditions for the rule correctly w/o rule memoization', () => {
        const rule = new Rule(
            'test-rule',
            [
                new Condition('factA', new EqualsOperator('abcd')),
                new Condition('factB', new GreaterThanInclusiveOperator(5)),
            ],
            [true, false],
        );

        expect(
            rule.evaluate(
                { factA: 'abcd', factB: 100 },
                { conditionResults: {} },
                {
                    memoizeRuleResult: false,
                    memoizeConditionResult: false,
                },
            ),
        ).toEqual({ type: 'SUCCESS', value: true });
    });

    test('should evaluate conditions for the rule correctly w/ rule memoization', () => {
        const rule = new Rule(
            'test-rule',
            [
                new Condition('factA', new EqualsOperator('abcd')),
                new Condition('factB', new GreaterThanInclusiveOperator(5)),
            ],
            [true, false],
        );
        const facts = { factA: 'abcd', factB: 100 };

        expect(
            rule.evaluate(
                facts,
                { conditionResults: {} },
                {
                    memoizeRuleResult: true,
                    memoizeConditionResult: false,
                },
            ),
        ).toEqual({ type: 'SUCCESS', value: true });
        expect(facts).toEqual({
            factA: 'abcd',
            factB: 100,
            'test-rule': true,
        });
    });
});
