package com.warnermedia.rulesengine

/**
 * Class defining the possible outputs of a condition evaluation
 */
sealed class ConditionResult {
    class Error(val errorMessage: String) : ConditionResult() {
        override fun toString(): String {
            return "ERROR: $errorMessage"
        }
    }

    class Ok(val okValue: Boolean) : ConditionResult() {
        override fun toString(): String {
            return "OK: $okValue"
        }
    }

    class Skipped(val skipReason: SkipReason) : ConditionResult() {
        override fun toString(): String {
            return "SKIPPED: ${skipReason.getSkipMessage()}"
        }
    }

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
