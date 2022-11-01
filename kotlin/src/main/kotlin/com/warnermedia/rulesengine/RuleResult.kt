package com.warnermedia.rulesengine

/**
 * Class defining the possible outputs of a rule evaluation
 */
sealed class RuleResult(val ruleId: String) {
    class Error(ruleId: String, val errorMessage: String) : RuleResult(ruleId) {
        override fun toString(): String {
            return "$ruleId -> ERROR: $errorMessage"
        }
    }

    class Failure(ruleId: String, val failureValue: Any) : RuleResult(ruleId) {
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

    class Skipped(ruleId: String, val skipReason: SkipReason) : RuleResult(ruleId) {
        override fun toString(): String {
            return "$ruleId -> SKIPPED: ${skipReason.getSkipMessage()}"
        }
    }

    class Success(ruleId: String, val successValue: Any) : RuleResult(ruleId) {
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

    fun isError(): Boolean {
        return this is Error
    }

    fun isFailure(): Boolean {
        return this is Failure
    }

    fun isSkipped(): Boolean {
        return this is Skipped
    }

    fun isSuccess(): Boolean {
        return this is Success
    }
}
