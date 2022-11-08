package com.warnermedia.rulesengine.core

/**
 * Enum defining different ways an engine will evaluate the rule set
 */
enum class EngineEvaluationType {
    ALL, FIRST_ERROR, FIRST_FAILURE, FIRST_SUCCESS
}