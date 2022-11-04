package core

/**
 * Class defining results from boolean operations like equals, greater than, etc.
 */
class Condition(
    val fact: String,
    val operator: Operator
) {
    fun evaluate(
        facts: HashMap<String, Any?>,
        conditionEvaluationOptions: ConditionEvaluationOptions
    ): ConditionResult {
        val valueFromFacts = facts[fact] ?: return when (conditionEvaluationOptions.undefinedFactEvaluationType) {
            UndefinedFactEvaluation.EVALUATE_TO_TRUE -> ConditionResult.Ok(true)
            UndefinedFactEvaluation.EVALUATE_TO_FALSE -> ConditionResult.Ok(false)
            UndefinedFactEvaluation.EVALUATE_TO_SKIPPED -> ConditionResult.Skipped(SkipReason.UNDEFINED_FACT)
        }

        return operator.operatorType.evaluate(operator.operatorValue, valueFromFacts, conditionEvaluationOptions)
    }
}
