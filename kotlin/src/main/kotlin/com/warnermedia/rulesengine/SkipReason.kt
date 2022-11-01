package com.warnermedia.rulesengine

/**
 * Enum class defining possible reasons to skip runtime evaluation
 */
enum class SkipReason {
    DISABLED_RULE {
        override fun getSkipMessage() = "rule is not enabled"
    },
    INACTIVE_RULE {
        override fun getSkipMessage() = "rule is not in active time range"
    },
    UNDEFINED_FACT {
        override fun getSkipMessage() = "runtime fact was undefined"
    };

    abstract fun getSkipMessage(): String
}
