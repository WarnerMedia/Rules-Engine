package com.warnermedia.rulesengine.core

/**
 * Condition
 *
 * @property fact
 * @property operator
 * @constructor Create empty Condition
 */
class Condition(
    val fact: String,
    val operator: Operator
) {
    /**
     * Evaluate
     *
     * @param facts
     * @param conditionEvaluationOptions
     * @return
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
