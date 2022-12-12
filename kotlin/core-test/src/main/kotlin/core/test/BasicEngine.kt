package core.test

import com.warnermedia.rulesengine.core.*

object BasicEngine {
    val engine = Engine(
        "basic-engine",
        arrayListOf(
            Rule(
                "basic-and-rule",
                arrayListOf(
                    Condition("fact1", Operator(OperatorType.EQUALS, "abcd")),
                    Condition("fact2", Operator(OperatorType.GREATER_THAN_EQUALS, 5)),
                ),
            ),
        ),
    )
}
