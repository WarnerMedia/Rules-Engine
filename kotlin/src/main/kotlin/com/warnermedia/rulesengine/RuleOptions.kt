package com.warnermedia.rulesengine

import java.time.Instant

class RuleOptions(
    val conditionJoiner: ConditionJoiner,
    enabled: Boolean?,
    startTime: Instant?,
    endTime: Instant?,
    priority: Short?
) {
    val enabled = enabled ?: true
    val startTime = (startTime ?: Instant.MIN).epochSecond
    val endTime = (endTime ?: Instant.MAX).epochSecond
    val priority = priority ?: 0
}
