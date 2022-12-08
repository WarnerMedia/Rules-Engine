package com.warnermedia.rulesengine.core

/**
 * Rule definition options
 *
 * @property conditionJoiner type of joiner for the rule's conditions
 * @property enabled boolean for if the rule is enabled or not
 * @property startTime start time for rule in epoch seconds
 * @property endTime end time for rule in epoch seconds
 * @property priority priority for the rule. higher mean more precedence
 * @constructor Create empty Rule options
 */
data class RuleOptions(
    val conditionJoiner: ConditionJoiner = ConditionJoiner.AND,
    val enabled: Boolean = true,
    val startTime: Int = Int.MIN_VALUE,
    val endTime: Int = Int.MAX_VALUE,
    val priority: Short = 0
)
