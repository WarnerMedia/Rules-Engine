package com.warnermedia.rulesengine.core

/**
 * Condition evaluation options required at runtime
 *
 * @property upcastFactValues upcast fact values at runtime to match operator value type
 * @property undefinedFactEvaluationType how to treat fact values not defined at runtime
 * @constructor Create empty Condition evaluation options
 */
data class ConditionEvaluationOptions(
    val upcastFactValues: Boolean,
    val undefinedFactEvaluationType: UndefinedFactEvaluation
)
