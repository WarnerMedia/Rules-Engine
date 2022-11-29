package com.warnermedia.rulesengine.core

/**
 * Class defining the possible outputs of a rule evaluation
 */
sealed class RuleResult(open val ruleId: String, open val conditionResults: List<ConditionResult>) {
    data class Error(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val errorMessage: String
    ) : RuleResult(ruleId, conditionResults)

    data class Failure(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val failureValue: Any
    ) : RuleResult(ruleId, conditionResults)

    data class Skipped(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val skipReason: SkipReason
    ) : RuleResult(ruleId, conditionResults)

    data class Success(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val successValue: Any
    ) : RuleResult(ruleId, conditionResults)

    fun isError(): Boolean {
        return this is Error
    }

    fun isFailure(): Boolean {
        return this is Failure
    }

    fun isSkipped(): Boolean {
        return this is Skipped
    }

    fun isSuccess(): Boolean {
        return this is Success
    }
}
