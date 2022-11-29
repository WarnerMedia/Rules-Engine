package com.warnermedia.rulesengine.core

/**
 * Class defining the possible outputs of an operator evaluation
 */
sealed class OperatorResult {
    data class Error(val errorMessage: String) : OperatorResult()

    data class Ok(val okValue: Boolean) : OperatorResult()

    data class Skipped(val skipReason: SkipReason) : OperatorResult()

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
