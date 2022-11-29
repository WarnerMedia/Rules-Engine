package com.warnermedia.rulesengine.core

/**
 * Class defining the possible outputs of a condition evaluation
 */
sealed class ConditionResult(open val fact: String, open val operatorType: String) {
    data class Error(override val fact: String, override val operatorType: String, val errorMessage: String) :
        ConditionResult(fact, operatorType)

    data class Ok(override val fact: String, override val operatorType: String, val okValue: Boolean) :
        ConditionResult(fact, operatorType)

    data class Skipped(override val fact: String, override val operatorType: String, val skipReason: SkipReason) :
        ConditionResult(fact, operatorType)

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
