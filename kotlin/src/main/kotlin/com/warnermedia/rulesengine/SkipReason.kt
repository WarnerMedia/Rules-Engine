package com.warnermedia.rulesengine

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
