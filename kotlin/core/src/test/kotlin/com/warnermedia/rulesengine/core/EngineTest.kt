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
    fun testSuccessfulEvaluation() {
        val result = engine.evaluate(hashMapOf("temperature" to 75, "rainfall" to 0))
        assertEquals(
            arrayListOf(
                RuleResult.Success("good-weather", arrayListOf(), "good-weather-day"),
            ),
            result.ruleEvaluations,
        )
    }

    @Test
    fun testSuccessfulEvaluationWithDetailedResult() {
        val result = engine.evaluate(
            hashMapOf("temperature" to 75, "rainfall" to 0),
            EngineEvaluationOptions(detailedEvaluationResults = true),
        )
        assertEquals(
            arrayListOf(
                RuleResult.Success(
                    "good-weather",
                    arrayListOf(
                        ConditionResult.Ok("temperature", OperatorType.GREATER_THAN.name, 70, true),
                        ConditionResult.Ok("rainfall", OperatorType.EQUALS.name, 0, true),
                    ),
                    "good-weather-day",
                ),
            ),
            result.ruleEvaluations,
        )
    }

    @Test
    fun testFailureEvaluation() {
        val result = engine.evaluate(hashMapOf("temperature" to 60, "rainfall" to 0))
        assertEquals(
            arrayListOf(
                RuleResult.Failure("good-weather", arrayListOf(), "work-from-home-day"),
            ),
            result.ruleEvaluations,
        )
    }

    @Test
    fun testFailureEvaluationWithDetailedEvaluation() {
        val result = engine.evaluate(
            hashMapOf("temperature" to 60, "rainfall" to 0),
            EngineEvaluationOptions(detailedEvaluationResults = true),
        )
        assertEquals(
            arrayListOf(
                RuleResult.Failure(
                    "good-weather",
                    arrayListOf(
                        ConditionResult.Ok("temperature", OperatorType.GREATER_THAN.name, 70, false),
                    ),
                    "work-from-home-day",
                ),
            ),
            result.ruleEvaluations,
        )
    }
}
