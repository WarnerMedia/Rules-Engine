package com.warnermedia.rulesengine.core

/**
 * Evaluation skip reason
 *
 * @constructor Create empty Skip reason
 */
enum class SkipReason {
    /**
     * Evaluation skipped due to disabled rule
     *
     * @constructor Create empty Disabled Rule
     */
    DISABLED_RULE {
        override fun getSkipMessage() = "rule is not enabled"
    },

    /**
     * Evaluation skipped due to rule not active
     *
     * @constructor Create empty Inactive Rule
     */
    INACTIVE_RULE {
        override fun getSkipMessage() = "rule is not in active time range"
    },

    /**
     * Evaluation skipped due to undefined fact at runtime
     *
     * @constructor Create empty Undefined Fact
     */
    UNDEFINED_FACT {
        override fun getSkipMessage() = "runtime fact was undefined"
    };

    /**
     * Get skip message
     *
     * @return skip message
     */
    abstract fun getSkipMessage(): String
}
