package com.warnermedia.rulesengine.core

import kotlin.test.Test
import kotlin.test.assertEquals

internal class EngineTest {
    private val engine = Engine(
        "weather-type-engine",
        arrayListOf(
            Rule(
                "good-weather",
                arrayListOf(
                    Condition("temperature", Operator(OperatorType.GREATER_THAN, 70)),
                    Condition("rainfall", Operator(OperatorType.EQUALS, 0)),
                ),
                Pair("good-weather-day", "work-from-home-day"),
            ),
        ),
    )

    @Test
    fun testSuccessfulResult() {
        val result = engine.evaluate(hashMapOf("temperature" to 75, "rainfall" to 0))
        println(result)
        assertEquals(
            arrayListOf(
                RuleResult.Success(
                    "good-weather",
                    arrayListOf(
                        ConditionResult.Ok("temperature", OperatorType.GREATER_THAN.name, true),
                        ConditionResult.Ok("rainfall", OperatorType.EQUALS.name, true),
                    ),
                    "good-weather-day",
                ),
            ),
            result.ruleEvaluations,
        )
    }

    @Test
    fun testFailureResult() {
        val result = engine.evaluate(hashMapOf("temperature" to 60, "rainfall" to 0))
        assertEquals(
            arrayListOf(RuleResult.Failure("good-weather",
                arrayListOf(
                    ConditionResult.Ok("temperature", OperatorType.GREATER_THAN.name, false),
                ),"work-from-home-day")),
            result.ruleEvaluations,
        )
    }
}
