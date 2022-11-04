package com.warnermedia.rulesengine.core

/**
 * Class defining options passed to a Rule during runtime evaluation
 */
class RuleEvaluationOptions(
    val upcastFactValues: Boolean,
    val undefinedFactEvaluationType: UndefinedFactEvaluation,
    val storeRuleEvaluationResults: Boolean
)
