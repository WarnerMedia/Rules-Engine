package com.warnermedia.rulesengine

import java.time.Instant

class Rule(
    val id: String,
    val conditions: ArrayList<Condition>,
    val result: Pair<Any, Any>,
    val options: RuleOptions
) {
    val ruleEvaluationErrorMessage = "rule evaluation error"

    fun evaluate(facts: HashMap<String, Any>, ruleEvaluationOptions: RuleEvaluationOptions): RuleResult {
        if (!options.enabled) {
            return getSkippedResult(SkipReason.DISABLED_RULE)
        }

        val currentTime = Instant.now().epochSecond

        if (currentTime < options.startTime || currentTime > options.endTime) {
            return getSkippedResult(SkipReason.INACTIVE_RULE)
        }

        return try {
            conditions.forEach {
                when (val evaluationResult =
                    it.evaluate(
                        facts,
                        ConditionEvaluationOptions(
                            ruleEvaluationOptions.upcastFactValues,
                            ruleEvaluationOptions.undefinedFactEvaluationType
                        )
                    )) {
                    is ConditionResult.Ok -> when (evaluationResult.okValue) {
                        true -> if (options.conditionJoiner == ConditionJoiner.OR) return getSuccessResult()
                        false -> if (options.conditionJoiner == ConditionJoiner.AND) return getFailureResult()
                    }

                    is ConditionResult.Skipped -> return getSkippedResult(evaluationResult.skipReason)
                    is ConditionResult.Error -> return getErrorResult(evaluationResult.errorMessage)
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

    private fun getSkippedResult(reason: SkipReason): RuleResult.Skipped {
        return RuleResult.Skipped(id, reason)
    }

    private fun getSuccessResult(): RuleResult.Success {
        return RuleResult.Success(id, result.first)
    }
}
