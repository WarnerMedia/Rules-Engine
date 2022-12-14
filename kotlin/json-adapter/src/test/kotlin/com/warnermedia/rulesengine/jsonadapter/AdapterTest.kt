package com.warnermedia.rulesengine.jsonadapter

import com.warnermedia.rulesengine.core.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AdapterTest {
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
    fun testPersistence() {
        Adapter.saveToFile(engine, "engine.json")
        val engineFromFile = Adapter.readFromFile("engine.json")
        val result = engineFromFile.evaluate(
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
}
