package core

/**
 * Class defining possible exit criteria for an engine evaluation
 */
sealed class ExitCriteria {
    class EarlyExit(val ruleResult: RuleResult) : ExitCriteria()

    object NormalExit : ExitCriteria()

    fun isEarlyExit(): Boolean {
        return this is EarlyExit
    }

    fun isNormalExit(): Boolean {
        return this is NormalExit
    }
}
