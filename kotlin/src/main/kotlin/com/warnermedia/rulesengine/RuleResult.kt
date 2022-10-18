package com.warnermedia.rulesengine

sealed class RuleResult {
    class Success(private val ruleId: String, private val successValue: Any) : RuleResult() {
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

    class Failure(private val ruleId: String, private val failureValue: Any) : RuleResult() {
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

    class Skipped(private val ruleId: String) : RuleResult() {
        override fun toString(): String {
            return "$ruleId -> SKIPPED"
        }
    }

    class Error(private val ruleId: String, private val errorMessage: String) : RuleResult() {
        override fun toString(): String {
            return "$ruleId -> ERROR: $errorMessage"
        }
    }
}
