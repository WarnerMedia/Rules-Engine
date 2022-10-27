package com.warnermedia.rulesengine

class EngineOptions(
    val evaluationType: EngineEvaluationType = EngineEvaluationType.ALL,
    val sortRulesByPriority: Boolean = false,
    val upcastFactValues: Boolean = false,
    val undefinedFactEvaluationType: UndefinedFactEvaluation = UndefinedFactEvaluation.EVALUATE_TO_SKIPPED,
    val storeRuleEvaluationResults: Boolean = false
)
