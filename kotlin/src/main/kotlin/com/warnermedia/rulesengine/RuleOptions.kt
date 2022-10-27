package com.warnermedia.rulesengine

class RuleOptions(
    val conditionJoiner: ConditionJoiner = ConditionJoiner.AND,
    val enabled: Boolean = true,
    val startTime: Int = Int.MIN_VALUE,
    val endTime: Int = Int.MAX_VALUE,
    val priority: Short = 0
)
