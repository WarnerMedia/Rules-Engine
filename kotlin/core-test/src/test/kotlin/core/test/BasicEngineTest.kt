package core.test

import com.warnermedia.rulesengine.core.*
import com.warnermedia.rulesengine.core.ExitCriteria.NormalExit
import kotlin.test.Test
import kotlin.test.assertEquals

class BasicEngineTest {

    @Test
    fun testSuccessEvaluationWithoutConditionResults() {
        val evaluationResult = BasicEngine.engine.evaluate(hashMapOf("fact1" to "abcd", "fact2" to 5))
        assertEquals(
            EvaluationResult(
                arrayListOf(
                    RuleResult.Success(
                        "basic-and-rule",
                        arrayListOf(),
                        true,
                    ),
                ),
                NormalExit,
            ),
            evaluationResult,
        )
    }

    @Test
    fun testSuccessEvaluationWithConditionResults() {
        val evaluationResult = BasicEngine.engine.evaluate(
            hashMapOf("fact1" to "abcd", "fact2" to 5),
            EngineEvaluationOptions(detailedEvaluationResults = true),
        )
        assertEquals(
            EvaluationResult(
                arrayListOf(
                    RuleResult.Success(
                        "basic-and-rule",
                        arrayListOf(
                            ConditionResult.Ok("fact1", OperatorType.EQUALS.name, "abcd", true),
                            ConditionResult.Ok("fact2", OperatorType.GREATER_THAN_EQUALS.name, 5, true),
                        ),
                        true,
                    ),
                ),
                NormalExit,
            ),
            evaluationResult,
        )
    }
}
