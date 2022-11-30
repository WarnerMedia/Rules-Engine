package com.warnermedia.rulesengine.core

/**
 * Operator result
 *
 * @constructor Create empty Operator result
 */
sealed class OperatorResult {
    /**
     * Error
     *
     * @property errorMessage
     * @constructor Create empty Error
     */
    data class Error(val errorMessage: String) : OperatorResult()

    /**
     * Ok
     *
     * @property okValue
     * @constructor Create empty Ok
     */
    data class Ok(val okValue: Boolean) : OperatorResult()

    /**
     * Skipped
     *
     * @property skipReason
     * @constructor Create empty Skipped
     */
    data class Skipped(val skipReason: SkipReason) : OperatorResult()

    /**
     * Is error
     *
     * @return
     */
    fun isError(): Boolean {
        return this is Error
    }

    /**
     * Is ok
     *
     * @return
     */
    fun isOk(): Boolean {
        return this is Ok
    }

    /**
     * Is skipped
     *
     * @return
     */
    fun isSkipped(): Boolean {
        return this is Skipped
    }
}
