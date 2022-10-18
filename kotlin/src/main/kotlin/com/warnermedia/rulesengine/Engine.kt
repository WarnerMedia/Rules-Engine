package com.warnermedia.rulesengine

class Engine(val id: String, rules: List<Rule>, private val options: EngineOptions) {
    private val rules = if (options.sortRulesByPriority) rules.sortedByDescending { it.options.priority } else rules

    fun evaluate(facts: Map<String, Any>): EvaluationResult {
        val evaluationResult = rules.evaluateEngineRulesLatestInclusive(facts, options.evaluationType)
        return EvaluationResult(
            evaluationResult.first,
            if (evaluationResult.second) ExitCriteria.EarlyExit(evaluationResult.first.last()) else ExitCriteria.NormalExit()
        )
    }

    private fun Iterable<Rule>.evaluateEngineRulesLatestInclusive(
        facts: Map<String, Any>, evaluationType: EngineEvaluationType
    ): Pair<List<RuleResult>, Boolean> {
        val list = ArrayList<RuleResult>()
        for (item in this) {
            val result = item.evaluate(facts)
            list.add(result)
            when (evaluationType) {
                EngineEvaluationType.FIRST_ERROR -> if (result is RuleResult.Error) return Pair(list, true)
                EngineEvaluationType.FIRST_FAILURE -> if (result is RuleResult.Failure) return Pair(list, true)
                EngineEvaluationType.FIRST_SUCCESS -> if (result is RuleResult.Success) return Pair(list, true)
                else -> continue
            }
        }
        return Pair(list, false)
    }
}
