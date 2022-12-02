package com.warnermedia.rulesengine.core

/**
 * Exit criteria for engine evaluation
 *
 * @constructor Create empty Exit criteria
 */
sealed class ExitCriteria {
    /**
     * Early exit from engine evaluation
     *
     * @property ruleResult
     * @constructor Create empty Early exit
     */
    data class EarlyExit(val ruleResult: RuleResult) : ExitCriteria()

    /**
     * Normal exit from engine evaluation
     *
     * todo: use `data object` starting kotlin 1.8
     *
     * @constructor Create empty Normal exit
     */
    object NormalExit : ExitCriteria() {
        override fun toString(): String {
            return "NormalExit"
        }
    }

    /**
     * Is early exit
     *
     * @return exit criteria is early exit
     */
    fun isEarlyExit(): Boolean {
        return this is EarlyExit
    }

    /**
     * Is normal exit
     *
     * @return exit criteria is normal exit
     */
    fun isNormalExit(): Boolean {
        return this is NormalExit
    }
}
