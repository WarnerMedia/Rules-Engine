package com.warnermedia.rulesengine.core

/**
 * Condition result
 *
 * @property fact
 * @property operatorType
 * @property operatorValue
 * @constructor Create empty Condition result
 */
sealed class ConditionResult(open val fact: String, open val operatorType: String, open val operatorValue: Any) {
    /**
     * Error
     *
     * @property fact
     * @property operatorType
     * @property operatorValue
     * @property errorMessage
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
     * Ok
     *
     * @property fact
     * @property operatorType
     * @property operatorValue
     * @property okValue
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
     * Skipped
     *
     * @property fact
     * @property operatorType
     * @property operatorValue
     * @property skipReason
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
     * @return
     */
    fun isError(): Boolean {
        return this is Error
    }

    /**
     * Is ok
     *
     * @return
     */
    fun isOk(): Boolean {
        return this is Ok
    }

    /**
     * Is skipped
     *
     * @return
     */
    fun isSkipped(): Boolean {
        return this is Skipped
    }
}
