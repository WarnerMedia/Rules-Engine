package com.warnermedia.rulesengine.core

/**
 * Class defining properties for a rules engine instance.
 * Mostly to be utilized during runtime fact evaluation
 */
data class EngineOptions(
    val evaluationType: EngineEvaluationType = EngineEvaluationType.ALL,
    val sortRulesByPriority: Boolean = false,
)
