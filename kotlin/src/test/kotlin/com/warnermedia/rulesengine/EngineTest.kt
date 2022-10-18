package com.warnermedia.rulesengine

import kotlin.test.Test
import kotlin.test.assertEquals

internal class EngineTest {
    private val engine = Engine(
        "test-engine",
        listOf(
            Rule(
                "test-rule",
                listOf(Condition("feature", Operator(OperatorType.EQUALS, 42))),
                Pair("success-result", "failure-result"),
                RuleOptions(ConditionJoiner.OR, true, null, null, null)
            )
        ), EngineOptions(EngineEvaluationType.ALL, false)
    )

    @Test
    fun testSuccessfulResult() {
        val result = engine.evaluate(mapOf("feature" to 42))
        assertEquals(arrayListOf(RuleResult.Success("test-rule", "success-result")), result.ruleEvaluations)
    }

    @Test
    fun testFailureResult() {
        val result = engine.evaluate(mapOf("feature" to 43))
        assertEquals(arrayListOf(RuleResult.Failure("test-rule", "failure-result")), result.ruleEvaluations)
    }
}
