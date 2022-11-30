package com.warnermedia.rulesengine.core

/**
 * Rule options
 *
 * @property conditionJoiner
 * @property enabled
 * @property startTime
 * @property endTime
 * @property priority
 * @constructor Create empty Rule options
 */
data class RuleOptions(
    val conditionJoiner: ConditionJoiner = ConditionJoiner.AND,
    val enabled: Boolean = true,
    val startTime: Int = Int.MIN_VALUE,
    val endTime: Int = Int.MAX_VALUE,
    val priority: Short = 0
)
