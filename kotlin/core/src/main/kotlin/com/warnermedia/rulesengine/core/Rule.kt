package com.warnermedia.rulesengine.core

import java.time.Instant

/**
 * Rule
 *
 * @property id
 * @property conditions
 * @property result
 * @property options
 * @constructor Create empty Rule
 */
class Rule @JvmOverloads constructor(
    val id: String,
    val conditions: List<Condition>,
    val result: Pair<Any, Any> = Pair(true, false),
    val options: RuleOptions = RuleOptions()
) {
    /**
     * Evaluate
     *
     * @param facts
     * @param ruleEvaluationOptions
     * @return
     */
    fun evaluate(facts: MutableMap<String, Any?>, ruleEvaluationOptions: RuleEvaluationOptions): RuleResult {
        if (!options.enabled) {
            return createSkippedResult(arrayListOf(), SkipReason.DISABLED_RULE)
        }

        val currentTime = Instant.now().epochSecond
        if (currentTime < options.startTime || currentTime > options.endTime) {
            return createSkippedResult(arrayListOf(), SkipReason.INACTIVE_RULE)
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

    private fun computeResult(
        facts: MutableMap<String, Any?>,
        ruleEvaluationOptions: RuleEvaluationOptions
    ): RuleResult {
        val conditionResults = arrayListOf<ConditionResult>()
        return conditions.firstNotNullOfOrNull {
            it.evaluate(facts, ruleEvaluationOptions.getConditionEvaluationOptions())
                .collectIntoConditional(conditionResults, ruleEvaluationOptions.detailedEvaluationResults)
                .getRuleResultOrNull(conditionResults)
        } ?: when (options.conditionJoiner) {
            ConditionJoiner.AND -> createSuccessResult(conditionResults)
            ConditionJoiner.OR -> createFailureResult(conditionResults)
        }
    }

    private fun ConditionResult.collectIntoConditional(
        list: MutableList<ConditionResult>, enabled: Boolean
    ): ConditionResult {
        if (enabled) list.add(this)
        return this
    }

    private fun ConditionResult.getRuleResultOrNull(conditionResults: List<ConditionResult>): RuleResult? {
        return when (this) {
            is ConditionResult.Ok -> when (this.okValue) {
                true -> if (options.conditionJoiner == ConditionJoiner.OR) createSuccessResult(conditionResults) else null
                false -> if (options.conditionJoiner == ConditionJoiner.AND) createFailureResult(conditionResults) else null
            }

            is ConditionResult.Skipped -> createSkippedResult(conditionResults, this.skipReason)
            is ConditionResult.Error -> createErrorResult(conditionResults, this.errorMessage)
        }
    }

    private fun RuleEvaluationOptions.getConditionEvaluationOptions(): ConditionEvaluationOptions {
        return ConditionEvaluationOptions(this.upcastFactValues, this.undefinedFactEvaluationType)
    }

    private fun createErrorResult(conditionResults: List<ConditionResult>, message: String): RuleResult.Error {
        return RuleResult.Error(id, conditionResults, message)
    }

    private fun createFailureResult(conditionResults: List<ConditionResult>): RuleResult.Failure {
        return RuleResult.Failure(id, conditionResults, result.second)
    }

    private fun createSkippedResult(conditionResults: List<ConditionResult>, reason: SkipReason): RuleResult.Skipped {
        return RuleResult.Skipped(id, conditionResults, reason)
    }

    private fun createSuccessResult(conditionResults: List<ConditionResult>): RuleResult.Success {
        return RuleResult.Success(id, conditionResults, result.first)
    }
}
