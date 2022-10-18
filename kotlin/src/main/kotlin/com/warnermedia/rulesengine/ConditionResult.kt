package com.warnermedia.rulesengine

sealed class ConditionResult {
    class Ok(val okValue: Boolean) : ConditionResult() {
        override fun toString(): String {
            return "ok result: $okValue"
        }
    }

    class Error(val errorMessage: String) : ConditionResult() {
        override fun toString(): String {
            return "error result: $errorMessage"
        }
    }
}
