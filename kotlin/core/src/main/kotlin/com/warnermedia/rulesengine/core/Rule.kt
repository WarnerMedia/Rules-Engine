package com.warnermedia.rulesengine.core

import java.time.Instant

/**
 * Class defining a set of conditions, success and failure result to use
 * upon evaluation of all the conditions
 */
class Rule @JvmOverloads constructor(
    val id: String,
    val conditions: ArrayList<Condition>,
    val result: Pair<Any, Any> = Pair(true, false),
    val options: RuleOptions = RuleOptions()
) {
    fun evaluate(facts: HashMap<String, Any?>, ruleEvaluationOptions: RuleEvaluationOptions): RuleResult {
        if (!options.enabled) {
            return getSkippedResult(SkipReason.DISABLED_RULE)
        }

        val currentTime = Instant.now().epochSecond
        if (currentTime < options.startTime || currentTime > options.endTime) {
            return getSkippedResult(SkipReason.INACTIVE_RULE)
        }

        val computedResult = computeResult(facts, ruleEvaluationOptions)
        if (ruleEvaluationOptions.storeRuleEvaluationResults) {
            when (computedResult) {
                is RuleResult.Success -> facts[this.id] = true
                is RuleResult.Failure -> facts[this.id] = false
                else -> Unit
            }
        }

        return computedResult
    }

    private fun computeResult(facts: HashMap<String, Any?>, ruleEvaluationOptions: RuleEvaluationOptions): RuleResult {
        return conditions.firstNotNullOfOrNull {
            it.evaluate(
                facts,
                ruleEvaluationOptions.getConditionEvaluationOptions(),
            ).getRuleResultOrNull()
        }
            ?: when (options.conditionJoiner) {
                ConditionJoiner.AND -> getSuccessResult()
                ConditionJoiner.OR -> getFailureResult()
            }
    }

    private fun ConditionResult.getRuleResultOrNull(): RuleResult? {
        when (this) {
            is ConditionResult.Ok -> when (this.okValue) {
                true -> if (options.conditionJoiner == ConditionJoiner.OR) return getSuccessResult()
                false -> if (options.conditionJoiner == ConditionJoiner.AND) return getFailureResult()
            }

            is ConditionResult.Skipped -> return getSkippedResult(this.skipReason)
            is ConditionResult.Error -> return getErrorResult(this.errorMessage)
        }

        return null
    }

    private fun RuleEvaluationOptions.getConditionEvaluationOptions(): ConditionEvaluationOptions {
        return ConditionEvaluationOptions(this.upcastFactValues, this.undefinedFactEvaluationType)
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
