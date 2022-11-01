package com.warnermedia.rulesengine

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
        assertEquals<ArrayList<RuleResult>>(
            arrayListOf(RuleResult.Success("good-weather", "good-weather-day")),
            result.ruleEvaluations,
        )
    }

    @Test
    fun testFailureResult() {
        val result = engine.evaluate(hashMapOf("temperature" to 60, "rainfall" to 0))
        assertEquals<ArrayList<RuleResult>>(
            arrayListOf(RuleResult.Failure("good-weather", "work-from-home-day")),
            result.ruleEvaluations,
        )
    }

    @Test
    fun testPersistence() {
        engine.saveToFile("engine.json")

        val engineFromFile = Engine.readFromFile("engine.json")
        val result = engineFromFile.evaluate(hashMapOf("temperature" to 75, "rainfall" to 0))
        assertEquals<ArrayList<RuleResult>>(
            arrayListOf(RuleResult.Success("good-weather", "good-weather-day")),
            result.ruleEvaluations,
        )
    }
}
