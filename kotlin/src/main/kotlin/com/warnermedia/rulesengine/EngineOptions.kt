package com.warnermedia.rulesengine

class EngineOptions(
    evaluationType: EngineEvaluationType?,
    sortRulesByPriority: Boolean?,
    upcastFactValues: Boolean?,
    undefinedFactEvaluationType: UndefinedFactEvaluation?
) {
    val evaluationType = evaluationType ?: EngineEvaluationType.ALL
    val sortRulesByPriority = sortRulesByPriority ?: false
    val upcastFactValues = upcastFactValues ?: false
    val undefinedFactEvaluationType = undefinedFactEvaluationType ?: UndefinedFactEvaluation.EVALUATE_TO_SKIPPED
}
