package com.warnermedia.rulesengine.core

/**
 * Class defining the possible outputs of a condition evaluation
 */
sealed class ConditionResult(open val fact: String, open val operator: String) {
    data class Error(override val fact: String, override val operator: String, val errorMessage: String) :
        ConditionResult(fact, operator)

    data class Ok(override val fact: String, override val operator: String, val okValue: Boolean) :
        ConditionResult(fact, operator)

    data class Skipped(override val fact: String, override val operator: String, val skipReason: SkipReason) :
        ConditionResult(fact, operator)

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
