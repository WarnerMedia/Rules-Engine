package com.warnermedia.rulesengine.core

/**
 * Class defining the possible outputs of a condition evaluation
 */
sealed class ConditionResult(open val fact: String, open val operatorType: String, open val operatorValue: Any) {
    data class Error(
        override val fact: String,
        override val operatorType: String,
        override val operatorValue: Any,
        val errorMessage: String
    ) :
        ConditionResult(fact, operatorType, operatorValue)

    data class Ok(
        override val fact: String,
        override val operatorType: String,
        override val operatorValue: Any,
        val okValue: Boolean
    ) :
        ConditionResult(fact, operatorType, operatorValue)

    data class Skipped(
        override val fact: String,
        override val operatorType: String,
        override val operatorValue: Any,
        val skipReason: SkipReason
    ) :
        ConditionResult(fact, operatorType, operatorValue)

    fun isError(): Boolean {
        return this is Error
    }

    fun isOk(): Boolean {
        return this is Ok
    }

    fun isSkipped(): Boolean {
        return this is Skipped
    }
}
