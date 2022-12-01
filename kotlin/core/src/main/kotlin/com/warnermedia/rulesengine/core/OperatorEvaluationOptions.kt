package com.warnermedia.rulesengine.core

/**
 * Operator evaluation options required at runtime
 *
 * @property upcastFactValues upcast fact values at runtime to match operator value type
 * @property undefinedFactEvaluationType how to treat fact values not defined at runtime
 * @constructor Create empty Operator evaluation options
 */
data class OperatorEvaluationOptions(
    val upcastFactValues: Boolean,
    val undefinedFactEvaluationType: UndefinedFactEvaluation
)
