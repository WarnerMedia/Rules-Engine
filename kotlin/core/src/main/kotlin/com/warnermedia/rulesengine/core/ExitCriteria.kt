package com.warnermedia.rulesengine.core

/**
 * Class defining possible exit criteria for an engine evaluation
 */
sealed class ExitCriteria {
    data class EarlyExit(val ruleResult: RuleResult) : ExitCriteria()

    object NormalExit : ExitCriteria() {
        override fun toString(): String {
            return "NormalExit"
        }
    }

    fun isEarlyExit(): Boolean {
        return this is EarlyExit
    }

    fun isNormalExit(): Boolean {
        return this is NormalExit
    }
}
