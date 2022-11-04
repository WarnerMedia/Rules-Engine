package com.warnermedia.rulesengine.core

/**
 * Class defining result of an engine evaluation of the rule set
 */
class EvaluationResult(val ruleEvaluations: ArrayList<RuleResult>, val exitCriteria: ExitCriteria) {
    override fun toString(): String {
        return ruleEvaluations.toString()
    }
}
