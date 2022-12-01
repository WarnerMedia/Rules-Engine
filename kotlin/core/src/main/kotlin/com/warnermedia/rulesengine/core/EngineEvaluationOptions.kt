package com.warnermedia.rulesengine.core

/**
 * Engine evaluation options required at runtime
 *
 * @property upcastFactValues upcast fact values at runtime to match operator value type
 * @property undefinedFactEvaluationType how to treat fact values not defined at runtime
 * @property storeRuleEvaluationResults store rule evaluations for use as facts
 * @property detailedEvaluationResults output condition evaluation results
 * @constructor Create empty Engine evaluation options
 */
data class EngineEvaluationOptions(
    val upcastFactValues: Boolean = false,
    val undefinedFactEvaluationType: UndefinedFactEvaluation = UndefinedFactEvaluation.EVALUATE_TO_SKIPPED,
    val storeRuleEvaluationResults: Boolean = false,
    val detailedEvaluationResults: Boolean = false
)
