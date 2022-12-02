package com.warnermedia.rulesengine.core

/**
 * Rule Engine to create a rule set for evaluation
 *
 * @property id rule engine ID
 * @property engineOptions options to define engine behavior
 * @property rules collection of rules to be evaluated
 * @constructor
 */
class Engine @JvmOverloads constructor(
    val id: String,
    rules: List<Rule>,
    val engineOptions: EngineOptions = EngineOptions()
) {
    val rules = if (engineOptions.sortRulesByPriority) rules.sortedByDescending { it.options.priority } else rules

    /**
     * Evaluate engine against facts
     *
     * @param facts data to evaluate engine against
     * @param engineEvaluationOptions options to use for engine evaluation
     * @return evaluation result
     */
    fun evaluate(
        facts: MutableMap<String, Any?>,
        engineEvaluationOptions: EngineEvaluationOptions = EngineEvaluationOptions()
    ): EvaluationResult {
        val evaluationResult = rules.evaluateEngineRulesLatestInclusive(facts, engineEvaluationOptions)
        val exitCriteria = when (val exitResult = evaluationResult.second) {
            null -> ExitCriteria.NormalExit
            else -> ExitCriteria.EarlyExit(exitResult)
        }

        return EvaluationResult(
            evaluationResult.first, exitCriteria,
        )
    }

    private fun EngineEvaluationOptions.toRuleEvaluationOptions(): RuleEvaluationOptions {
        return RuleEvaluationOptions(
            this.upcastFactValues,
            this.undefinedFactEvaluationType,
            this.storeRuleEvaluationResults,
            this.detailedEvaluationResults,
        )
    }

    private fun Iterable<Rule>.evaluateEngineRulesLatestInclusive(
        facts: MutableMap<String, Any?>,
        engineEvaluationOptions: EngineEvaluationOptions
    ): Pair<List<RuleResult>, RuleResult?> {
        val list = ArrayList<RuleResult>()
        for (item in this) {
            val result = item.evaluate(
                facts,
                engineEvaluationOptions.toRuleEvaluationOptions(),
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
