package com.warnermedia.rulesengine.core

/**
 * Result from an operator evaluation
 *
 * @constructor Create empty Operator result
 */
sealed class OperatorResult {
    /**
     * Operator result for an error evaluation
     *
     * @property errorMessage the error message for operator evaluation
     * @constructor Create empty Error
     */
    data class Error(val errorMessage: String) : OperatorResult()

    /**
     * Operator result for an ok evaluation
     *
     * @property okValue the boolean result for operator evaluation
     * @constructor Create empty Ok
     */
    data class Ok(val okValue: Boolean) : OperatorResult()

    /**
     * Operator result for a skipped evaluation
     *
     * @property skipReason the skip reason for operator evaluation
     * @constructor Create empty Skipped
     */
    data class Skipped(val skipReason: SkipReason) : OperatorResult()

    /**
     * Is error
     *
     * @return operator result is error type
     */
    fun isError(): Boolean {
        return this is Error
    }

    /**
     * Is ok
     *
     * @return operator result is ok type
     */
    fun isOk(): Boolean {
        return this is Ok
    }

    /**
     * Is skipped
     *
     * @return operator result is skipped type
     */
    fun isSkipped(): Boolean {
        return this is Skipped
    }
}
