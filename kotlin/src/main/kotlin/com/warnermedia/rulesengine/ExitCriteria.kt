package com.warnermedia.rulesengine

sealed class ExitCriteria() {
    class EarlyExit(private val ruleResult: RuleResult) : ExitCriteria()

    class NormalExit : ExitCriteria()
}
