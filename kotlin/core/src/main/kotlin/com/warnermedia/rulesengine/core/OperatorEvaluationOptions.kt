package com.warnermedia.rulesengine.core

/**
 * Class defining options passed during evaluation of an operator
 */
data class OperatorEvaluationOptions(
    val upcastFactValues: Boolean,
    val undefinedFactEvaluationType: UndefinedFactEvaluation
)
