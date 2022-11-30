package com.warnermedia.rulesengine.core

/**
 * Rule evaluation options
 *
 * @property upcastFactValues
 * @property undefinedFactEvaluationType
 * @property storeRuleEvaluationResults
 * @property detailedEvaluationResults
 * @constructor Create empty Rule evaluation options
 */
data class RuleEvaluationOptions(
    val upcastFactValues: Boolean,
    val undefinedFactEvaluationType: UndefinedFactEvaluation,
    val storeRuleEvaluationResults: Boolean,
    val detailedEvaluationResults: Boolean
)
