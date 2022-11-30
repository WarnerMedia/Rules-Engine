package com.warnermedia.rulesengine.core

/**
 * Engine options
 *
 * @property evaluationType
 * @property sortRulesByPriority
 * @constructor Create empty Engine options
 */
data class EngineOptions(
    val evaluationType: EngineEvaluationType = EngineEvaluationType.ALL,
    val sortRulesByPriority: Boolean = false,
)
