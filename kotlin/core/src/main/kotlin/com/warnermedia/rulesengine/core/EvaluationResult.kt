package com.warnermedia.rulesengine.core

/**
 * Engine evaluation result
 *
 * @property ruleEvaluations collection of rule evaluations
 * @property exitCriteria exit criteria for
 * @constructor Create empty Evaluation result
 */
data class EvaluationResult(val ruleEvaluations: List<RuleResult>, val exitCriteria: ExitCriteria)
