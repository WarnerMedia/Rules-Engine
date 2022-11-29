package com.warnermedia.rulesengine.core

/**
 * Class defining the possible outputs of a condition evaluation
 */
sealed class ConditionResult {
    data class Error(val errorMessage: String) : ConditionResult()

    data class Ok(val okValue: Boolean) : ConditionResult()

    data class Skipped(val skipReason: SkipReason) : ConditionResult()

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
