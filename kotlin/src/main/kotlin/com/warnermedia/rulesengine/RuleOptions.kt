package com.warnermedia.rulesengine

class RuleOptions(
    val conditionJoiner: ConditionJoiner, enabled: Boolean?, startTime: Int?, endTime: Int?, priority: Short?
) {
    val enabled = enabled ?: true
    val startTime = startTime ?: Int.MIN_VALUE
    val endTime = endTime ?: Int.MAX_VALUE
    val priority = priority ?: 0
}
