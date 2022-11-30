package com.warnermedia.rulesengine.core

/**
 * Evaluation result
 *
 * @property ruleEvaluations
 * @property exitCriteria
 * @constructor Create empty Evaluation result
 */
data class EvaluationResult(val ruleEvaluations: List<RuleResult>, val exitCriteria: ExitCriteria)
