package com.warnermedia.rulesengine

class EngineOptions(evaluationType: EngineEvaluationType?, sortRulesByPriority: Boolean?, upcastFactValues: Boolean?) {
    val evaluationType = evaluationType ?: EngineEvaluationType.ALL
    val sortRulesByPriority = sortRulesByPriority ?: false
    val upcastFactValues = upcastFactValues ?: false
}
