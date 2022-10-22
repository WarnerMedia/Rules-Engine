package com.warnermedia.rulesengine

import java.time.Instant

class Rule(
    private val id: String,
    private val conditions: ArrayList<Condition>,
    private val result: Pair<Any, Any>,
    val options: RuleOptions
) {
    private val ruleEvaluationErrorMessage = "rule evaluation error"

    fun evaluate(facts: HashMap<String, Any>, ruleEvaluationOptions: RuleEvaluationOptions): RuleResult {
        if (!options.enabled) {
            return getSkippedResult()
        }

        val currentTime = Instant.now().epochSecond

        if (currentTime < options.startTime || currentTime > options.endTime) {
            return getSkippedResult()
        }

        return try {
            conditions.forEach {
                when (val evaluationResult =
                    it.evaluate(facts, ConditionEvaluationOptions(ruleEvaluationOptions.upcastFactValues))) {
                    is ConditionResult.Error -> return getErrorResult(evaluationResult.errorMessage)
                    is ConditionResult.Ok -> when (evaluationResult.okValue) {
                        true -> if (options.conditionJoiner == ConditionJoiner.OR) return getSuccessResult()
                        false -> if (options.conditionJoiner == ConditionJoiner.AND) return getFailureResult()
                    }
                }
            }

            return if (options.conditionJoiner == ConditionJoiner.AND) getSuccessResult() else getFailureResult()
        } catch (e: Exception) {
            getErrorResult(e.message ?: ruleEvaluationErrorMessage)
        }
    }

    private fun getErrorResult(message: String): RuleResult.Error {
        return RuleResult.Error(id, message)
    }

    private fun getFailureResult(): RuleResult.Failure {
        return RuleResult.Failure(id, result.second)
    }

    private fun getSkippedResult(): RuleResult.Skipped {
        return RuleResult.Skipped(id)
    }

    private fun getSuccessResult(): RuleResult.Success {
        return RuleResult.Success(id, result.first)
    }
}
