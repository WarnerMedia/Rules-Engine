package com.warnermedia.rulesengine.core

/**
 * Class defining a rules engine instance with an input rule set and other options
 */
class Engine(val id: String, rules: ArrayList<Rule>, val options: EngineOptions = EngineOptions()) {
    val rules = if (options.sortRulesByPriority) rules.sortedByDescending { it.options.priority } else rules

    fun evaluate(facts: HashMap<String, Any?>): EvaluationResult {
        val evaluationResult = rules.evaluateEngineRulesLatestInclusive(facts, options)
        return EvaluationResult(
            evaluationResult.first,
            if (evaluationResult.second) {
                ExitCriteria.EarlyExit(evaluationResult.first.last())
            } else {
                ExitCriteria.NormalExit
            },
        )
    }

    private fun Iterable<Rule>.evaluateEngineRulesLatestInclusive(
        facts: HashMap<String, Any?>, engineOptions: EngineOptions
    ): Pair<ArrayList<RuleResult>, Boolean> {
        val list = ArrayList<RuleResult>()
        for (item in this) {
            val result = item.evaluate(
                facts,
                RuleEvaluationOptions(
                    engineOptions.upcastFactValues,
                    engineOptions.undefinedFactEvaluationType,
                    engineOptions.storeRuleEvaluationResults,
                ),
            )
            list.add(result)
            when (engineOptions.evaluationType) {
                EngineEvaluationType.FIRST_ERROR -> if (result.isError()) return Pair(list, true)
                EngineEvaluationType.FIRST_FAILURE -> if (result.isFailure()) return Pair(list, true)
                EngineEvaluationType.FIRST_SUCCESS -> if (result.isSuccess()) return Pair(list, true)
                else -> Unit
            }
        }
        return Pair(list, false)
    }
}
