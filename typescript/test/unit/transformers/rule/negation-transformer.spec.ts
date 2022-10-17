import {
    Condition,
    EqualsOperator,
    GreaterThanInclusiveOperator,
    notRule,
    Rule,
} from '../../../../src';

describe('Rule', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    test('should set options correctly', () => {
        const rule = new Rule(
            'test-rule',
            [
                new Condition('factA', new EqualsOperator('abcd')),
                new Condition('factB', new GreaterThanInclusiveOperator(5)),
            ],
            [true, false],
        );
        const negatedRule = notRule(rule, { name: 'not-rule-name', result: [false, true] });

        expect(negatedRule.name).toEqual('not-rule-name');
        expect(
            negatedRule.evaluate(
                { factA: 'abcde', factB: 100 },
                { conditionResults: {} },
                {
                    memoizeRuleResult: false,
                    memoizeConditionResult: false,
                },
            ),
        ).toEqual({ type: 'SUCCESS', value: false });
    });

    test('should evaluate conditions for the rule correctly to true', () => {
        const rule = new Rule(
            'test-rule',
            [
                new Condition('factA', new EqualsOperator('abcd')),
                new Condition('factB', new GreaterThanInclusiveOperator(5)),
            ],
            [true, false],
        );
        const negatedRule = notRule(rule);

        expect(
            negatedRule.evaluate(
                { factA: 'abcde', factB: 100 },
                { conditionResults: {} },
                {
                    memoizeRuleResult: false,
                    memoizeConditionResult: false,
                },
            ),
        ).toEqual({ type: 'SUCCESS', value: true });
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
        const negatedRule = notRule(rule);

        expect(
            negatedRule.evaluate(
                { factA: 'abcd', factB: 100 },
                { conditionResults: {} },
                {
                    memoizeRuleResult: false,
                    memoizeConditionResult: false,
                },
            ),
        ).toEqual({ type: 'FAILURE', value: false });
    });
});
