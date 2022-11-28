package com.warnermedia.rulesengine.core

/**
 * Class defining the possible outputs of a rule evaluation
 */
sealed class RuleResult(open val ruleId: String) {
    data class Error(override val ruleId: String, val errorMessage: String) : RuleResult(ruleId)

    data class Failure(override val ruleId: String, val failureValue: Any) : RuleResult(ruleId)

    data class Skipped(override val ruleId: String, val skipReason: SkipReason) : RuleResult(ruleId)

    data class Success(override val ruleId: String, val successValue: Any) : RuleResult(ruleId)

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
