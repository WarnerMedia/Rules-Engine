package com.warnermedia.rulesengine.core

/**
 * Rule evaluation result
 *
 * @property ruleId unique rule ID
 * @property conditionResults collection of condition results for the rule
 * @constructor Create empty Rule result
 */
sealed class RuleResult(open val ruleId: String, open val conditionResults: List<ConditionResult>) {
    /**
     * Rule result for error evaluation
     *
     * @property ruleId unique rule ID
     * @property conditionResults collection of condition results for the rule
     * @property errorMessage the error message for rule evaluation
     * @constructor Create empty Error
     */
    data class Error(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val errorMessage: String
    ) : RuleResult(ruleId, conditionResults)

    /**
     * Rule result for failure evaluation
     *
     * @property ruleId unique rule ID
     * @property conditionResults collection of condition results for the rule
     * @property failureValue the failure value for rule evaluation
     * @constructor Create empty Failure
     */
    data class Failure(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val failureValue: Any
    ) : RuleResult(ruleId, conditionResults)

    /**
     * Rule result for skipped evaluation
     *
     * @property ruleId unique rule ID
     * @property conditionResults collection of condition results for the rule
     * @property skipReason the skip reason for skipped evaluation
     * @constructor Create empty Skipped
     */
    data class Skipped(
        override val ruleId: String,
        override val conditionResults: List<ConditionResult>,
        val skipReason: SkipReason
    ) : RuleResult(ruleId, conditionResults)

    /**
     * Rule result for success evaluation
     *
     * @property ruleId unique rule ID
     * @property conditionResults collection of condition results for the rule
     * @property successValue the success value for rule evaluation
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
     * @return rule result is error type
     */
    fun isError(): Boolean {
        return this is Error
    }

    /**
     * Is failure
     *
     * @return rule result is failure type
     */
    fun isFailure(): Boolean {
        return this is Failure
    }

    /**
     * Is skipped
     *
     * @return rule result is skipped type
     */
    fun isSkipped(): Boolean {
        return this is Skipped
    }

    /**
     * Is success
     *
     * @return rule result is success type
     */
    fun isSuccess(): Boolean {
        return this is Success
    }
}
