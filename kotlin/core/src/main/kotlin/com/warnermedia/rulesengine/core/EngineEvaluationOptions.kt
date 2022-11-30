package com.warnermedia.rulesengine.core

/**
 * Engine evaluation options
 *
 * @property upcastFactValues
 * @property undefinedFactEvaluationType
 * @property storeRuleEvaluationResults
 * @property detailedEvaluationResults
 * @constructor Create empty Engine evaluation options
 */
data class EngineEvaluationOptions(
    val upcastFactValues: Boolean = false,
    val undefinedFactEvaluationType: UndefinedFactEvaluation = UndefinedFactEvaluation.EVALUATE_TO_SKIPPED,
    val storeRuleEvaluationResults: Boolean = false,
    val detailedEvaluationResults: Boolean = false
)
