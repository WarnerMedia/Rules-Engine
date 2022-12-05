package com.warnermedia.rulesengine.core

/**
 * Result from a condition evaluation
 *
 * @property fact the fact condition result is for
 * @property operatorType the operator type condition result is for
 * @property operatorValue the operator value condition result is for
 * @constructor Create empty Condition result
 */
sealed class ConditionResult(open val fact: String, open val operatorType: String, open val operatorValue: Any) {
    /**
     * Condition result for an error during evaluation
     *
     * @property fact the fact condition result is for
     * @property operatorType the operator type condition result is for
     * @property operatorValue the operator value condition result is for
     * @property errorMessage the error message for condition evaluation
     * @constructor Create empty Error
     */
    data class Error(
        override val fact: String,
        override val operatorType: String,
        override val operatorValue: Any,
        val errorMessage: String
    ) :
        ConditionResult(fact, operatorType, operatorValue)

    /**
     * Condition result for a successful or failure evaluation
     *
     * @property fact the fact condition result is for
     * @property operatorType the operator type condition result is for
     * @property operatorValue the operator value condition result is for
     * @property okValue boolean result of condition evaluation
     * @constructor Create empty Ok
     */
    data class Ok(
        override val fact: String,
        override val operatorType: String,
        override val operatorValue: Any,
        val okValue: Boolean
    ) :
        ConditionResult(fact, operatorType, operatorValue)

    /**
     * Condition result for a skipped evaluation
     *
     * @property fact the fact condition result is for
     * @property operatorType the operator type condition result is for
     * @property operatorValue the operator value condition result is for
     * @property skipReason the reason to skip condition evaluation
     * @constructor Create empty Skipped
     */
    data class Skipped(
        override val fact: String,
        override val operatorType: String,
        override val operatorValue: Any,
        val skipReason: SkipReason
    ) :
        ConditionResult(fact, operatorType, operatorValue)

    /**
     * Is error
     *
     * @return condition result is error type
     */
    fun isError(): Boolean {
        return this is Error
    }

    /**
     * Is ok
     *
     * @return condition result is ok type
     */
    fun isOk(): Boolean {
        return this is Ok
    }

    /**
     * Is skipped
     *
     * @return condition result is skipped type
     */
    fun isSkipped(): Boolean {
        return this is Skipped
    }
}
