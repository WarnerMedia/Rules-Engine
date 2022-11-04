package com.warnermedia.rulesengine.core

/**
 * Class defining options passed during evaluation of a condition
 */
class ConditionEvaluationOptions(
    val upcastFactValues: Boolean,
    val undefinedFactEvaluationType: UndefinedFactEvaluation
)
