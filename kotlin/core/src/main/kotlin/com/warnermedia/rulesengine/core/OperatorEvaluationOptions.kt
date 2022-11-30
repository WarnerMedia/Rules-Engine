package com.warnermedia.rulesengine.core

/**
 * Operator evaluation options
 *
 * @property upcastFactValues
 * @property undefinedFactEvaluationType
 * @constructor Create empty Operator evaluation options
 */
data class OperatorEvaluationOptions(
    val upcastFactValues: Boolean,
    val undefinedFactEvaluationType: UndefinedFactEvaluation
)
