/* eslint-disable */

import { performance } from 'perf_hooks';
import {
    ArrayContainsOperator,
    Condition,
    EqualsOperator,
    NotEqualsOperator,
    NotInRangeOperator,
    Rule,
    RuleEngine,
    RuleEngineEvaluationPolicy,
} from '../../src';

const RUNS_PER_RULE_COUNT = 10_000;
const NUM_RULES = 50;

const noMemoRecord: number[] = [];

const noMemoRecordNormalized: number[] = [];

function fn() {
    for (let numRules = 0; numRules < NUM_RULES; numRules++) {
        const rules = [
            new Rule(
                'apple-sub-group-rule',
                [
                    new Condition('sessionPaymentProvider', new EqualsOperator('apple')),
                    new Condition('skuPurchaseType', new EqualsOperator('NEW')),
                    new Condition(
                        'skuName',
                        new ArrayContainsOperator([
                            'sku-alternate-group-1',
                            'sku-alternate-group-2',
                        ]),
                    ),
                ],
                [true, false],
            ),
            new Rule(
                'LRAP-rule',
                [
                    new Condition(
                        'sessionPaymentProvider',
                        new ArrayContainsOperator(['google', 'blackmarket']),
                    ),
                    new Condition(
                        'skuName',
                        new ArrayContainsOperator(['frictionless-sku-1', 'frictionless-sku-2']),
                    ),
                ],

                [true, false],
            ),
            new Rule(
                'mobile-only-sku-rule',
                [
                    new Condition('skuPlanType', new EqualsOperator('MOBILE_ONLY')),
                    new Condition('sessionPlatformType', new NotEqualsOperator('MOBILE')),
                ],
                [true, false],
            ),
            new Rule(
                'roku-instant-sign-up-rule',
                [
                    new Condition('sessionPaymentProvider', new EqualsOperator('roku')),
                    new Condition(
                        'skuName',
                        new ArrayContainsOperator(['roku-isu-avod-sku', 'roku-isu-svod-sku']),
                    ),
                    new Condition(
                        'sessionRokuActivationDate',
                        new NotInRangeOperator({ start: 'start-timestamp', end: 'end-timestamp' }),
                    ),
                ],
                [true, false],
            ),
        ];

        for (let index = 0; index < numRules; index++) {
            rules.push(
                new Rule(
                    `is-not-apple-${index}`,
                    [
                        new Condition('skuName', new EqualsOperator(`sku-${index}`)),
                        new Condition('sessionPaymentProvider', new EqualsOperator('apple')),
                    ],
                    [true, false],
                ),
            );
        }

        const iapRulesNoMemoization: RuleEngine = new RuleEngine(
            'iap-plan-picker-rules-no-memo',
            rules,
            {
                evaluationPolicy: RuleEngineEvaluationPolicy.EVALUATE_ALL,
                memoizeConditionResults: false,
            },
        );

        const facts = {
            sessionPaymentProvider: 'apple',
            skuPurchaseType: 'NEW',
            skuPlanType: 'MOBILE_ONLY',
            sessionPlatformType: 'MOBILE_ONLY',
        };

        const noMemoPerf = [];

        for (let i = 0; i < RUNS_PER_RULE_COUNT; i++) {
            const t1 = performance.now();
            const iapRulesNoMemoizationEval = iapRulesNoMemoization.evaluate(facts);
            const t2 = performance.now();

            noMemoPerf.push(t2 - t1);
        }

        noMemoPerf.sort();

        const noMemo = noMemoPerf[RUNS_PER_RULE_COUNT / 2]!;

        noMemoRecord.push(noMemo);
    }

    for (let i = 0; i < NUM_RULES; i++) {
        noMemoRecordNormalized.push(noMemoRecord[i]! / noMemoRecord[i]!);
    }
}

fn();

const noMemoAvg = (
    noMemoRecord.reduce((previousValue, currentValue) => previousValue + currentValue, 0) /
    NUM_RULES
).toPrecision(3);

console.log(`no optimization: ${noMemoAvg}ms (baseline)`);
