package com.warnermedia.rulesengine

/**
 * Enum defining how to evaluate a runtime fact that is not defined
 */
enum class UndefinedFactEvaluation {
    EVALUATE_TO_TRUE, EVALUATE_TO_FALSE, EVALUATE_TO_SKIPPED
}
