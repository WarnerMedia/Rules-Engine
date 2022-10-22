package com.warnermedia.rulesengine

class Condition(
    val fact: String,
    val operator: Operator,
) {
    fun evaluate(facts: HashMap<String, Any>, conditionEvaluationOptions: ConditionEvaluationOptions): ConditionResult {
        val valueFromFacts = facts[fact] ?: return ConditionResult.Ok(false)

        return operator.operatorType.evaluate(operator.operatorValue, valueFromFacts, conditionEvaluationOptions)
    }
}
