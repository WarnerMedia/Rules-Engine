import * as fs from 'fs';

import {
    Condition,
    EqualsOperator,
    ResultType,
    Rule,
    RuleEngine,
    RuleEngineEvaluationPolicy,
} from '../../../src';

jest.mock('fs');

describe('RuleEngine', () => {
    const sampleJsonRepresentation = {
        name: 'test-engine',
        rules: [
            {
                name: 'test-rule-one',
                result: [true, false],
                options: { priority: 0, disabled: false },
                conditions: [
                    {
                        fact: 'factA',
                        operator: { operatorType: 'EQUALS', valueToCompareAgainst: 'abcd' },
                        options: { ignoreUndefined: false, useSHA1ForMemoizedName: false },
                    },
                ],
            },
            {
                name: 'test-rule-two',
                result: [false, true],
                options: { priority: 0, disabled: false },
                conditions: [
                    {
                        fact: 'factB',
                        operator: { operatorType: 'EQUALS', valueToCompareAgainst: 3 },
                        options: { ignoreUndefined: false, useSHA1ForMemoizedName: false },
                    },
                    {
                        fact: 'factA',
                        operator: { operatorType: 'NOT_EQUALS', valueToCompareAgainst: 'abcde' },
                        options: { ignoreUndefined: false, useSHA1ForMemoizedName: false },
                    },
                ],
            },
        ],
        options: {
            evaluationPolicy: 'EVALUATE_ALL',
            memoizeConditionResults: false,
            memoizeRuleResults: false,
            sortRulesByPriority: false,
        },
        version: 1,
    };

    let readFileSyncEngine: jest.Mock;
    let readFileSyncRules: jest.Mock;
    let writeFileSync: jest.Mock;
    beforeEach(() => {
        readFileSyncEngine = jest.fn().mockReturnValue(JSON.stringify(sampleJsonRepresentation));
        readFileSyncRules = jest
            .fn()
            .mockReturnValue(JSON.stringify({ rules: sampleJsonRepresentation.rules, version: 1 }));
        writeFileSync = jest.fn();
        jest.clearAllMocks();
        jest.resetModules();
        (fs.writeFileSync as jest.Mock).mockImplementation(writeFileSync);
    });

    describe('create', () => {
        test('should create new instance correctly w/ default options', () => {
            const engine = new RuleEngine('test-engine', []);

            expect(engine.name).toEqual('test-engine');
            expect(engine.rules).toHaveLength(0);
            expect(engine.options).toEqual({
                evaluationPolicy: 'EVALUATE_ALL',
                memoizeConditionResults: false,
                memoizeRuleResults: false,
                sortRulesByPriority: false,
            });
        });

        test('should create new instance correctly w/o default options', () => {
            const engine = new RuleEngine('test-engine', [], {
                evaluationPolicy: RuleEngineEvaluationPolicy.EVALUATE_TO_FIRST_SUCCESS,
                memoizeConditionResults: true,
                memoizeRuleResults: true,
                sortRulesByPriority: true,
            });

            expect(engine.name).toEqual('test-engine');
            expect(engine.rules).toHaveLength(0);
            expect(engine.options).toEqual({
                evaluationPolicy: 'EVALUATE_TO_FIRST_SUCCESS',
                memoizeConditionResults: true,
                memoizeRuleResults: true,
                sortRulesByPriority: true,
            });
        });

        test('should create new instance correctly and save to disk', () => {
            const engine = new RuleEngine('test-engine', [
                new Rule(
                    'test-rule-one',
                    [new Condition('factA', new EqualsOperator('abcd'))],
                    [true, false],
                ),
                new Rule(
                    'test-rule-two',
                    [new Condition('factB', new EqualsOperator(3))],
                    [false, true],
                    {},
                ),
            ]);

            engine.saveEngine(__dirname + '/test-path.json');

            expect(writeFileSync).toHaveBeenCalled();
        });

        test('should create new instance correctly and save rules to disk', () => {
            const engine = new RuleEngine('test-engine', [
                new Rule(
                    'test-rule-one',
                    [new Condition('factA', new EqualsOperator('abcd'))],
                    [true, false],
                ),
                new Rule(
                    'test-rule-two',
                    [new Condition('factB', new EqualsOperator(3))],
                    [false, true],
                    {},
                ),
            ]);

            engine.saveRules(__dirname + '/test-path.json');

            expect(writeFileSync).toHaveBeenCalled();
        });
    });

    describe('evaluate', () => {
        test('should evaluate a single rule correctly', () => {
            const engine = new RuleEngine('test-engine', [
                new Rule(
                    'test-rule-one',
                    [new Condition('factA', new EqualsOperator('abcd'))],
                    [true, false],
                ),
            ]);

            const result = engine.evaluate({ factA: 'abcd' });
            expect(result).toEqual({
                ruleEvaluations: [
                    {
                        name: 'test-rule-one',
                        result: { type: 'SUCCESS', value: true },
                    },
                ],
                exitCriteria: { earlyExit: false },
            });
        });

        test('should evaluate w/ multiple rules correctly', () => {
            const engine = new RuleEngine('test-engine', [
                new Rule(
                    'test-rule-one',
                    [new Condition('factA', new EqualsOperator('abcd'))],
                    [true, false],
                ),
                new Rule(
                    'test-rule-two',
                    [new Condition('factB', new EqualsOperator(3))],
                    [false, true],
                    {},
                ),
            ]);

            const result = engine.evaluate({ factA: 'abcd', factB: 3 });
            expect(result).toEqual({
                ruleEvaluations: [
                    { name: 'test-rule-one', result: { type: 'SUCCESS', value: true } },
                    { name: 'test-rule-two', result: { type: 'SUCCESS', value: false } },
                ],
                exitCriteria: { earlyExit: false },
            });
        });

        test('should evaluate w/ disabled rule correctly', () => {
            const engine = new RuleEngine('test-engine', [
                new Rule(
                    'test-rule-one',
                    [new Condition('factA', new EqualsOperator('abcd'))],
                    [true, false],
                ),
                new Rule(
                    'test-rule-two',
                    [new Condition('factB', new EqualsOperator(3))],
                    [true, false],
                    {
                        disabled: true,
                    },
                ),
            ]);

            const result = engine.evaluate({ factA: 'abcd' });
            expect(result).toEqual({
                ruleEvaluations: [
                    { name: 'test-rule-one', result: { type: 'SUCCESS', value: true } },
                    { name: 'test-rule-two', result: { type: 'SKIPPED' } },
                ],
                exitCriteria: { earlyExit: false },
            });
        });

        test('should short circuit evaluation correctly for success', () => {
            const engine = new RuleEngine(
                'test-engine',
                [
                    new Rule(
                        'test-rule-one',
                        [new Condition('factA', new EqualsOperator('abcde'))],
                        [true, false],
                    ),
                    new Rule(
                        'test-rule-two',
                        [new Condition('factB', new EqualsOperator(3))],
                        [true, false],
                        {},
                    ),
                ],
                { evaluationPolicy: RuleEngineEvaluationPolicy.EVALUATE_TO_FIRST_SUCCESS },
            );

            const result = engine.evaluate({ factA: 'abcd', factB: 3 });
            expect(result).toEqual({
                ruleEvaluations: [
                    { name: 'test-rule-one', result: { type: 'FAILURE', value: false } },
                    { name: 'test-rule-two', result: { type: 'SUCCESS', value: true } },
                ],
                exitCriteria: {
                    earlyExit: true,
                    rule: {
                        name: 'test-rule-two',
                        result: { type: 'SUCCESS', value: true },
                    },
                },
            });
        });

        test('should short circuit evaluation correctly for failure', () => {
            const engine = new RuleEngine(
                'test-engine',
                [
                    new Rule(
                        'test-rule-one',
                        [new Condition('factA', new EqualsOperator('abcde'))],
                        [true, false],
                    ),
                    new Rule(
                        'test-rule-two',
                        [new Condition('factB', new EqualsOperator(3))],
                        [true, false],
                        {},
                    ),
                ],
                { evaluationPolicy: RuleEngineEvaluationPolicy.EVALUATE_TO_FIRST_FAILURE },
            );

            const result = engine.evaluate({ factA: 'abcd', factB: 3 });
            expect(result).toEqual({
                ruleEvaluations: [
                    { name: 'test-rule-one', result: { type: 'FAILURE', value: false } },
                ],
                exitCriteria: {
                    earlyExit: true,
                    rule: {
                        name: 'test-rule-one',
                        result: { type: 'FAILURE', value: false },
                    },
                },
            });
        });

        test('should evaluate w/ rule result memoization', () => {
            const engine = new RuleEngine(
                'test-engine',
                [
                    new Rule(
                        'test-rule-one',
                        [new Condition('factA', new EqualsOperator('abcd'))],
                        [true, false],
                    ),
                    new Rule(
                        'test-rule-two',
                        [new Condition('factB', new EqualsOperator(3))],
                        [false, true],
                        {
                            priority: 1,
                        },
                    ),
                    new Rule(
                        'test-rule-three',
                        [new Condition('test-rule-two', new EqualsOperator(true))],
                        [true, false],
                        {},
                    ),
                ],
                { sortRulesByPriority: true, memoizeRuleResults: true },
            );

            const result = engine.evaluate({ factA: 'abcd', factB: 3 });
            expect(result).toEqual({
                ruleEvaluations: [
                    { name: 'test-rule-two', result: { type: 'SUCCESS', value: false } },
                    { name: 'test-rule-one', result: { type: 'SUCCESS', value: true } },
                    { name: 'test-rule-three', result: { type: 'SUCCESS', value: true } },
                ],
                exitCriteria: { earlyExit: false },
            });
        });

        test('should evaluate w/ condition result memoization', () => {
            const engine = new RuleEngine(
                'test-engine',
                [
                    new Rule(
                        'test-rule-one',
                        [new Condition('factA', new EqualsOperator('abcd'))],
                        [true, false],
                    ),
                    new Rule(
                        'test-rule-two',
                        [new Condition('factB', new EqualsOperator(3))],
                        [false, true],
                    ),
                    new Rule(
                        'test-rule-three',
                        [
                            new Condition('factB', new EqualsOperator(3)),
                            new Condition('factA', new EqualsOperator('abcd')),
                            new Condition('factC', new EqualsOperator(true)),
                        ],
                        [true, false],
                    ),
                    new Rule(
                        'test-rule-four',
                        [
                            new Condition('factB', new EqualsOperator(false)),
                            new Condition('factB', new EqualsOperator(5)),
                        ],
                        [true, false],
                    ),
                    new Rule(
                        'test-rule-five',
                        [
                            new Condition('factB', new EqualsOperator(4)),
                            new Condition('factC', new EqualsOperator(true)),
                            new Condition('factA', new EqualsOperator('abcdf')),
                        ],
                        [true, false],
                    ),
                ],
                { memoizeConditionResults: true },
            );

            const result = engine.evaluate({ factA: 'abcd', factB: 3 });
            expect(result).toEqual({
                ruleEvaluations: [
                    { name: 'test-rule-one', result: { type: 'SUCCESS', value: true } },
                    { name: 'test-rule-two', result: { type: 'SUCCESS', value: false } },
                    { name: 'test-rule-three', result: { type: 'FAILURE', value: false } },
                    { name: 'test-rule-four', result: { type: 'FAILURE', value: false } },
                    { name: 'test-rule-five', result: { type: 'FAILURE', value: false } },
                ],
                exitCriteria: { earlyExit: false },
            });
        });
    });

    describe('validation', () => {
        test('should error when the rule names are repeated', () => {
            const engine = () =>
                new RuleEngine('test-engine', [
                    new Rule('test-rule-one', [], [true, false]),
                    new Rule('test-rule-one', [], [true, false]),
                ]);

            expect(engine).toThrowError('two rules found with the same name: test-rule-one');
        });

        test('should error when the rule priority is out of range', () => {
            const engine = () =>
                new RuleEngine('test-engine', [
                    new Rule('test-rule-one', [], [true, false], { priority: -1 }),
                ]);

            expect(engine).toThrowError('rule priority out of bounds for rule: test-rule-one');
        });
    });

    describe('test', () => {
        test('should test engine successfully', () => {
            const engine = new RuleEngine('test-engine', [
                new Rule(
                    'test-rule-one',
                    [new Condition('factA', new EqualsOperator('abcd'))],
                    [true, false],
                ),
                new Rule(
                    'test-rule-two',
                    [new Condition('factB', new EqualsOperator(3))],
                    [false, true],
                    {},
                ),
            ]);

            const testResult = () =>
                engine.testEngine(
                    { factA: 'abcd', factB: 3 },
                    {
                        ruleEvaluations: [
                            {
                                name: 'test-rule-one',
                                result: { type: ResultType.SUCCESS, value: true },
                            },
                            {
                                name: 'test-rule-two',
                                result: { type: ResultType.SUCCESS, value: false },
                            },
                        ],
                        exitCriteria: { earlyExit: false },
                    },
                );
            expect(testResult).not.toThrowError();
        });

        test('should test rule successfully', () => {
            const engine = new RuleEngine('test-engine', [
                new Rule(
                    'test-rule-one',
                    [new Condition('factA', new EqualsOperator('abcd'))],
                    [true, false],
                ),
                new Rule(
                    'test-rule-two',
                    [new Condition('factB', new EqualsOperator(3))],
                    [false, true],
                    {},
                ),
            ]);

            const testResult = () =>
                engine.testRule(
                    'test-rule-one',
                    { factA: 'abcd', factB: 3 },
                    { type: ResultType.SUCCESS, value: true },
                );
            expect(testResult).not.toThrowError();
        });

        test('should throw error for test rule when rule does not exist', () => {
            const engine = new RuleEngine('test-engine', [
                new Rule(
                    'test-rule-one',
                    [new Condition('factA', new EqualsOperator('abcd'))],
                    [true, false],
                ),
                new Rule(
                    'test-rule-two',
                    [new Condition('factB', new EqualsOperator(3))],
                    [false, true],
                    {},
                ),
            ]);

            const testFn = () =>
                engine.testRule(
                    'test-rule-three',
                    { factA: 'abcd', factB: 3 },
                    { type: ResultType.SUCCESS, value: true },
                );
            expect(testFn).toThrowError();
        });
    });

    describe('read from disk', () => {
        describe('engine', () => {
            test('should read engine from disk', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(readFileSyncEngine);
                const engine = RuleEngine.readEngineFromDisk('test-engine');

                expect(engine.name).toEqual('test-engine');
                expect(engine.rules).toHaveLength(2);
                expect(engine.options).toEqual({
                    evaluationPolicy: 'EVALUATE_ALL',
                    memoizeConditionResults: false,
                    memoizeRuleResults: false,
                    sortRulesByPriority: false,
                });
            });

            test('should error when reading incomplete engine from disk', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(JSON.stringify({})),
                );
                const engine = () => RuleEngine.readEngineFromDisk('test-engine');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading incomplete engine from disk - no name', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(JSON.stringify({})),
                );
                const engine = () => RuleEngine.readEngineFromDisk('test-engine');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading incomplete engine from disk - no options', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(JSON.stringify({ name: 'test-engine' })),
                );
                const engine = () => RuleEngine.readEngineFromDisk('test-engine');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading incomplete engine from disk - no rules', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(JSON.stringify({ name: 'test-engine', options: {} })),
                );
                const engine = () => RuleEngine.readEngineFromDisk('test-engine');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading incomplete engine from disk - no version', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest
                        .fn()
                        .mockReturnValue(
                            JSON.stringify({ name: 'test-engine', options: {}, rules: [] }),
                        ),
                );
                const engine = () => RuleEngine.readEngineFromDisk('test-engine');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading wrong engine from disk - incorrect version', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(
                        JSON.stringify({
                            name: 'test-engine',
                            options: {},
                            rules: [],
                            version: 2,
                        }),
                    ),
                );
                const engine = () => RuleEngine.readEngineFromDisk('test-engine');

                expect(engine).toThrowError('unsupported engine version number: 2');
            });
        });

        describe('rules', () => {
            test('should read rules from disk', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(readFileSyncRules);
                const rules = RuleEngine.readRulesFromDisk('test-rules');

                expect(rules).toHaveLength(2);
                expect(rules[0]?.name).toEqual('test-rule-one');
                expect(rules[1]?.name).toEqual('test-rule-two');
            });

            test('should error when reading incomplete rules from disk - no rules', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(JSON.stringify({})),
                );
                const engine = () => RuleEngine.readRulesFromDisk('test-rules');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading incomplete rules from disk - no version', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(JSON.stringify({ rules: [{}] })),
                );
                const engine = () => RuleEngine.readRulesFromDisk('test-rules');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading incomplete rules from disk - malformed rules', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(JSON.stringify({ rules: {}, version: 1 })),
                );
                const engine = () => RuleEngine.readRulesFromDisk('test-rules');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading incomplete rules from disk - no name', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(JSON.stringify({ rules: [{}], version: 1 })),
                );
                const engine = () => RuleEngine.readRulesFromDisk('test-rules');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading incomplete rules from disk - no conditions', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest
                        .fn()
                        .mockReturnValue(
                            JSON.stringify({ rules: [{ name: 'test-rule' }], version: 1 }),
                        ),
                );
                const engine = () => RuleEngine.readRulesFromDisk('test-rules');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading incomplete rules from disk - no result', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(
                        JSON.stringify({
                            rules: [{ name: 'test-rule', conditions: [] }],
                            version: 1,
                        }),
                    ),
                );
                const engine = () => RuleEngine.readRulesFromDisk('test-rules');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading incomplete rules from disk - no options', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(
                        JSON.stringify({
                            rules: [{ name: 'test-rule', conditions: [], result: true }],
                            version: 1,
                        }),
                    ),
                );
                const engine = () => RuleEngine.readRulesFromDisk('test-rules');

                expect(engine).toThrowError('wrong file contents');
            });

            test('should error when reading wrong rules from disk - incorrect version', () => {
                (fs.readFileSync as jest.Mock).mockImplementation(
                    jest.fn().mockReturnValue(
                        JSON.stringify({
                            rules: [
                                { name: 'test-rule', conditions: [], result: true, options: {} },
                            ],
                            version: 2,
                        }),
                    ),
                );
                const engine = () => RuleEngine.readRulesFromDisk('test-engine');

                expect(engine).toThrowError('unsupported rules version number: 2');
            });
        });
    });

    describe('miscellaneous', () => {
        test('should get memoization details correctly', () => {
            const engine = new RuleEngine('test-engine', [
                new Rule(
                    'test-rule-one',
                    [new Condition('factA', new EqualsOperator('abcd'))],
                    [true, false],
                ),
                new Rule(
                    'test-rule-two',
                    [new Condition('factA', new EqualsOperator('abcde'))],
                    [true, false],
                ),
                new Rule(
                    'test-rule-three',
                    [
                        new Condition('factA', new EqualsOperator('abcde')),
                        new Condition('factB', new EqualsOperator(2)),
                    ],
                    [true, false],
                ),
            ]);

            const memoizationDetails = engine.getMemoizationDetails();
            expect(memoizationDetails.possibleConditionsWithMemoizedResult).toBeDefined();
            expect(memoizationDetails.possibleConditionsWithMemoizedResult).toEqual('1/4');
        });
    });
});
