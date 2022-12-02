package com.warnermedia.rulesengine.core

/**
 * Behavior for undefined fact at runtime
 *
 * @constructor Create empty Undefined fact evaluation
 */
enum class UndefinedFactEvaluation {
    /**
     * Evaluate condition for undefined fact to true
     *
     * @constructor Create empty Evaluate To True
     */
    EVALUATE_TO_TRUE,

    /**
     * Evaluate condition for undefined fact to false
     *
     * @constructor Create empty Evaluate To False
     */
    EVALUATE_TO_FALSE,

    /**
     * Evaluate condition for undefined fact to skipped
     *
     * @constructor Create empty Evaluate To Skipped
     */
    EVALUATE_TO_SKIPPED
}
