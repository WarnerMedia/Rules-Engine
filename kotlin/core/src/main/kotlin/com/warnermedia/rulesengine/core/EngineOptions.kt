package com.warnermedia.rulesengine.core

/**
 * Engine definition options
 *
 * @property evaluationType type of rule set evaluation
 * @property sortRulesByPriority boolean to sort rules based on priority prior to execution
 * @constructor Create empty Engine options
 */
data class EngineOptions(
    val evaluationType: EngineEvaluationType = EngineEvaluationType.ALL,
    val sortRulesByPriority: Boolean = false,
)
