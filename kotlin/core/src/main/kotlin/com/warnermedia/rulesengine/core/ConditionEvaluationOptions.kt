package com.warnermedia.rulesengine.core

/**
 * Condition evaluation options
 *
 * @property upcastFactValues
 * @property undefinedFactEvaluationType
 * @constructor Create empty Condition evaluation options
 */
data class ConditionEvaluationOptions(
    val upcastFactValues: Boolean,
    val undefinedFactEvaluationType: UndefinedFactEvaluation
)
