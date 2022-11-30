package com.warnermedia.rulesengine.core

/**
 * Exit criteria
 *
 * @constructor Create empty Exit criteria
 */
sealed class ExitCriteria {
    /**
     * Early exit
     *
     * @property ruleResult
     * @constructor Create empty Early exit
     */
    data class EarlyExit(val ruleResult: RuleResult) : ExitCriteria()

    // todo: use `data object` starting kotlin 1.8
    object NormalExit : ExitCriteria() {
        override fun toString(): String {
            return "NormalExit"
        }
    }

    /**
     * Is early exit
     *
     * @return
     */
    fun isEarlyExit(): Boolean {
        return this is EarlyExit
    }

    /**
     * Is normal exit
     *
     * @return
     */
    fun isNormalExit(): Boolean {
        return this is NormalExit
    }
}
