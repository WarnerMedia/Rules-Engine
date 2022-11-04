package core

/**
 * Class defining properties for a rules engine instance.
 * Mostly to be utilized during runtime fact evaluation
 */
class EngineOptions(
    val evaluationType: EngineEvaluationType = EngineEvaluationType.ALL,
    val sortRulesByPriority: Boolean = false,
    val upcastFactValues: Boolean = false,
    val undefinedFactEvaluationType: UndefinedFactEvaluation = UndefinedFactEvaluation.EVALUATE_TO_SKIPPED,
    val storeRuleEvaluationResults: Boolean = false
)
