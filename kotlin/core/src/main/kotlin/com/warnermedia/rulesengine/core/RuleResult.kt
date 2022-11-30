package com.warnermedia.rulesengine.core

/**
 * Rule result
 *
 * @property ruleId
 * @property conditionResults
 * @constructor Create empty Rule result
 */
sealed class RuleResult(open val ruleId: String, open val conditionResults: List<ConditionResult>) {
    /**
     * Error
     *
     * @property ruleId
     * @property conditionResults
     * @property errorMessage
     * @constructor Create empty Error
     */
    data class Error(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val errorMessage: String
    ) : RuleResult(ruleId, conditionResults)

    /**
     * Failure
     *
     * @property ruleId
     * @property conditionResults
     * @property failureValue
     * @constructor Create empty Failure
     */
    data class Failure(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val failureValue: Any
    ) : RuleResult(ruleId, conditionResults)

    /**
     * Skipped
     *
     * @property ruleId
     * @property conditionResults
     * @property skipReason
     * @constructor Create empty Skipped
     */
    data class Skipped(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val skipReason: SkipReason
    ) : RuleResult(ruleId, conditionResults)

    /**
     * Success
     *
     * @property ruleId
     * @property conditionResults
     * @property successValue
     * @constructor Create empty Success
     */
    data class Success(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val successValue: Any
    ) : RuleResult(ruleId, conditionResults)

    /**
     * Is error
     *
     * @return
     */
    fun isError(): Boolean {
        return this is Error
    }

    /**
     * Is failure
     *
     * @return
     */
    fun isFailure(): Boolean {
        return this is Failure
    }

    /**
     * Is skipped
     *
     * @return
     */
    fun isSkipped(): Boolean {
        return this is Skipped
    }

    /**
     * Is success
     *
     * @return
     */
    fun isSuccess(): Boolean {
        return this is Success
    }
}
