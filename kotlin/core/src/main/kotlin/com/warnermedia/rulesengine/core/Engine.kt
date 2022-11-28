package com.warnermedia.rulesengine.core

/**
 * Class defining a rules engine instance with an input rule set and other options
 */
class Engine @JvmOverloads constructor(
    val id: String,
    rules: ArrayList<Rule>,
    val options: EngineOptions = EngineOptions()
) {
    val rules = if (options.sortRulesByPriority) rules.sortedByDescending { it.options.priority } else rules

    fun evaluate(facts: HashMap<String, Any?>): EvaluationResult {
        val evaluationResult = rules.evaluateEngineRulesLatestInclusive(facts, options)
        val exitCriteria = when (val exitResult = evaluationResult.second) {
            null -> ExitCriteria.NormalExit
            else -> ExitCriteria.EarlyExit(exitResult)
        }

        return EvaluationResult(
            evaluationResult.first, exitCriteria,
        )
    }

    private fun Iterable<Rule>.evaluateEngineRulesLatestInclusive(
        facts: HashMap<String, Any?>, engineOptions: EngineOptions
    ): Pair<ArrayList<RuleResult>, RuleResult?> {
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
                EngineEvaluationType.FIRST_ERROR -> if (result.isError()) return Pair(list, result)
                EngineEvaluationType.FIRST_FAILURE -> if (result.isFailure()) return Pair(list, result)
                EngineEvaluationType.FIRST_SUCCESS -> if (result.isSuccess()) return Pair(list, result)
                else -> Unit
            }
        }
        return Pair(list, null)
    }
}
