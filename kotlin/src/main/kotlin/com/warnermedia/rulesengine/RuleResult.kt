package com.warnermedia.rulesengine

sealed class RuleResult {
    class Success(val ruleId: String, val successValue: Any) : RuleResult() {
        override fun toString(): String {
            return "$ruleId -> SUCCESS: $successValue"
        }

        override fun equals(other: Any?): Boolean {
            return other is Success && this.successValue == other.successValue
        }

        override fun hashCode(): Int {
            return successValue.hashCode()
        }
    }

    class Failure(val ruleId: String, val failureValue: Any) : RuleResult() {
        override fun toString(): String {
            return "$ruleId -> FAILURE: $failureValue"
        }

        override fun equals(other: Any?): Boolean {
            return other is Failure && this.failureValue == other.failureValue
        }

        override fun hashCode(): Int {
            return failureValue.hashCode()
        }
    }

    class Skipped(val ruleId: String, val skipReason: SkipReason) : RuleResult() {
        override fun toString(): String {
            return "$ruleId -> SKIPPED: ${skipReason.getSkipMessage()}"
        }
    }

    class Error(val ruleId: String, val errorMessage: String) : RuleResult() {
        override fun toString(): String {
            return "$ruleId -> ERROR: $errorMessage"
        }
    }
}
