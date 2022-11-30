package com.warnermedia.rulesengine.core

/**
 * Class defining properties for a rules engine evaluation
 */
data class EngineEvaluationOptions(
    val upcastFactValues: Boolean = false,
    val undefinedFactEvaluationType: UndefinedFactEvaluation = UndefinedFactEvaluation.EVALUATE_TO_SKIPPED,
    val storeRuleEvaluationResults: Boolean = false,
    val detailedEvaluationResults: Boolean = false
)
