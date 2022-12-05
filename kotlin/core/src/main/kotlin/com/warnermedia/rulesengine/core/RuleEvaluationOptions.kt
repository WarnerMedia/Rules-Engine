package com.warnermedia.rulesengine.core

/**
 * Rule evaluation options
 *
 * @property upcastFactValues upcast fact values at runtime to match operator value type
 * @property undefinedFactEvaluationType how to treat fact values not defined at runtime
 * @property storeRuleEvaluationResults store rule evaluations for use as facts
 * @property detailedEvaluationResults output condition evaluation results
 * @constructor Create empty Rule evaluation options
 */
data class RuleEvaluationOptions(
    val upcastFactValues: Boolean,
    val undefinedFactEvaluationType: UndefinedFactEvaluation,
    val storeRuleEvaluationResults: Boolean,
    val detailedEvaluationResults: Boolean
)
