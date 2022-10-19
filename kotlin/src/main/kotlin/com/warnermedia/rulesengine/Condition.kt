package com.warnermedia.rulesengine

class Condition(private val fact: String, private val operator: Operator) {
    fun evaluate(facts: Map<String, Any>): ConditionResult {
        val valueFromFacts = facts[fact] ?: return ConditionResult.Ok(false)

        return operator.operatorType.evaluate(operator.operatorValue, valueFromFacts)
    }
}
