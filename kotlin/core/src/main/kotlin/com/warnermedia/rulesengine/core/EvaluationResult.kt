package com.warnermedia.rulesengine.core

/**
 * Class defining result of an engine evaluation of the rule set
 */
data class EvaluationResult(val ruleEvaluations: List<RuleResult>, val exitCriteria: ExitCriteria)
