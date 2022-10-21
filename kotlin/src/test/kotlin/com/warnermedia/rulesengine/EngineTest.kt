package com.warnermedia.rulesengine

import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import java.io.File
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
        ), EngineOptions(EngineEvaluationType.ALL, sortRulesByPriority = false, upcastFactValues = true)
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

    @Test
    fun testPersistence() {
        val gson = GsonBuilder().setPrettyPrinting().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create()
        val str = gson.toJson(engine)

        File("abc.json").writeText(str, Charsets.UTF_8)
        val readInString = File("abc.json").readText(Charsets.UTF_8)
        val readInStringAsObject = gson.fromJson(readInString, Engine::class.java)

        val result = readInStringAsObject.evaluate(mapOf("feature" to 42))
        assertEquals(arrayListOf(RuleResult.Failure("test-rule", "failure-result")), result.ruleEvaluations)
    }
}
