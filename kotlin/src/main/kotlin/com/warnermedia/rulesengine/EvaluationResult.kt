package com.warnermedia.rulesengine

class EvaluationResult(val ruleEvaluations: List<RuleResult>, val exitCriteria: ExitCriteria) {
    override fun toString(): String {
        return ruleEvaluations.toString()
    }
}
