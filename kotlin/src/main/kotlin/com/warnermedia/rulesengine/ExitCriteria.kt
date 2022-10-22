package com.warnermedia.rulesengine

sealed class ExitCriteria {
    class EarlyExit(val ruleResult: RuleResult) : ExitCriteria()

    class NormalExit : ExitCriteria()
}
