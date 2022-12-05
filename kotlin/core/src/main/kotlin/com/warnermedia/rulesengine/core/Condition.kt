package com.warnermedia.rulesengine.core

/**
 * An operator and a fact that leads to a boolean output
 *
 * @property fact the value to get from runtime facts
 * @property operator the operator type leading to boolean result
 * @constructor Create empty Condition
 */
class Condition(
    val fact: String,
    val operator: Operator
) {
    /**
     * Evaluate the condition against runtime facts
     *
     * @param facts data to evaluate condition against
     * @param conditionEvaluationOptions options to use for condition evaluation
     * @return condition evaluation result
     */
    fun evaluate(
        facts: MutableMap<String, Any?>,
        conditionEvaluationOptions: ConditionEvaluationOptions
    ): ConditionResult {
        val valueFromFacts = facts[fact] ?: return when (conditionEvaluationOptions.undefinedFactEvaluationType) {
            UndefinedFactEvaluation.EVALUATE_TO_TRUE -> ConditionResult.Ok(
                fact,
                operator.operatorType.name,
                operator.operatorValue,
                true,
            )

            UndefinedFactEvaluation.EVALUATE_TO_FALSE -> ConditionResult.Ok(
                fact,
                operator.operatorType.name,
                operator.operatorValue,
                false,
            )

            UndefinedFactEvaluation.EVALUATE_TO_SKIPPED -> ConditionResult.Skipped(
                fact,
                operator.operatorType.name,
                operator.operatorValue,
                SkipReason.UNDEFINED_FACT,
            )
        }

        return operator.operatorType.evaluate(
            operator.operatorValue,
            valueFromFacts,
            conditionEvaluationOptions.getOperatorEvaluationOptions(),
        ).getConditionResult()
    }

    private fun ConditionEvaluationOptions.getOperatorEvaluationOptions(): OperatorEvaluationOptions {
        return OperatorEvaluationOptions(this.upcastFactValues, this.undefinedFactEvaluationType)
    }

    private fun OperatorResult.getConditionResult(): ConditionResult {
        return when (this) {
            is OperatorResult.Error -> ConditionResult.Error(
                fact,
                operator.operatorType.name,
                operator.operatorValue,
                this.errorMessage,
            )

            is OperatorResult.Ok -> ConditionResult.Ok(
                fact,
                operator.operatorType.name,
                operator.operatorValue,
                this.okValue,
            )

            is OperatorResult.Skipped -> ConditionResult.Skipped(
                fact,
                operator.operatorType.name,
                operator.operatorValue,
                this.skipReason,
            )
        }
    }
}
