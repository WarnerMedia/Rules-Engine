package com.warnermedia.rulesengine.core

/**
 * Skip reason
 *
 * @constructor Create empty Skip reason
 */
enum class SkipReason {
    /**
     * Disabled Rule
     *
     * @constructor Create empty Disabled Rule
     */
    DISABLED_RULE {
        override fun getSkipMessage() = "rule is not enabled"
    },

    /**
     * Inactive Rule
     *
     * @constructor Create empty Inactive Rule
     */
    INACTIVE_RULE {
        override fun getSkipMessage() = "rule is not in active time range"
    },

    /**
     * Undefined Fact
     *
     * @constructor Create empty Undefined Fact
     */
    UNDEFINED_FACT {
        override fun getSkipMessage() = "runtime fact was undefined"
    };

    /**
     * Get skip message
     *
     * @return
     */
    abstract fun getSkipMessage(): String
}
