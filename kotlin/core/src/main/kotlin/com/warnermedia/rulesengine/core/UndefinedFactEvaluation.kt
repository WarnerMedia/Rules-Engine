package com.warnermedia.rulesengine.core

/**
 * Undefined fact evaluation
 *
 * @constructor Create empty Undefined fact evaluation
 */
enum class UndefinedFactEvaluation {
    /**
     * Evaluate To True
     *
     * @constructor Create empty Evaluate To True
     */
    EVALUATE_TO_TRUE,

    /**
     * Evaluate To False
     *
     * @constructor Create empty Evaluate To False
     */
    EVALUATE_TO_FALSE,

    /**
     * Evaluate To Skipped
     *
     * @constructor Create empty Evaluate To Skipped
     */
    EVALUATE_TO_SKIPPED
}
