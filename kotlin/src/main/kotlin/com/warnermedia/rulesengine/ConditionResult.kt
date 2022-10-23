package com.warnermedia.rulesengine

sealed class ConditionResult {
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

    class Error(val errorMessage: String) : ConditionResult() {
        override fun toString(): String {
            return "ERROR: $errorMessage"
        }
    }
}
