# Core

## Engine

### Options

Optional values to be passed during engine creation. If not passed, each one uses the default value

```kotlin
class EngineOptions(
    val evaluationType: EngineEvaluationType = EngineEvaluationType.ALL,
    val sortRulesByPriority: Boolean = false,
    val upcastFactValues: Boolean = false,
    val undefinedFactEvaluationType: UndefinedFactEvaluation = UndefinedFactEvaluation.EVALUATE_TO_SKIPPED,
    val storeRuleEvaluationResults: Boolean = false
)
```

- `evaluationType` - Rule evaluation criteria. Defaults to evaluate all rules. Other values 
can be used to short-circuit evaluation upon first success/failure
- `sortRulesByPriority` - Arrange rules in descending order of priority before evaluation.
Defaults to `false`
- `upcastFactValues` - Upcast values at runtime to the type of the operator value (if possible).
Defaults to `false`
- `undefinedFactEvaluationType` - Evaluation criteria for facts not defined at runtime. Defaults
to treating them as 'skipped'. Can be instead treated as success/failure
- `storeRuleEvaluationResults` - Store result of rule evaluations to be used in condition for
other rules. Defaults to `false`

## Rule

### Options

Optional values to be passed during rule creation. If not passed, each one uses the default value

```kotlin
class RuleOptions(
    val conditionJoiner: ConditionJoiner = ConditionJoiner.AND,
    val enabled: Boolean = true,
    val startTime: Int = Int.MIN_VALUE,
    val endTime: Int = Int.MAX_VALUE,
    val priority: Short = 0
)
```

- `conditionJoiner` - Operand to combine results of conditions within same rule. Defaults to `AND`.
`OR` can be used instead
- `enabled` - Flag to decide whether the rule will be evaluated or not. Defaults to `true`
- `startTime` - Start time in epoch seconds for the rule to be enabled. Defaults to `Int.MIN_VALUE`
- `endTime` - End time in epoch seconds for the rule to be enabled until.
Defaults to `Int.MAX_VALUE`
- `priority` - Numeric importance of the rule. Defaults to `0`. Only relevant if
`sortRulesByPriority` is `true` for the engine

## Basic usage

Assuming `com.warnermedia.rulesengine:rulesengine-core` is installed

```kotlin
import com.warnermedia.rulesengine.core.*

val engine = Engine(
    "day-type-engine",
    arrayListOf(
        Rule(
            "sunny-day",
            arrayListOf(
                Condition("temperature", Operator(OperatorType.GREATER_THAN, 60)),
                Condition("rainfall", Operator(OperatorType.EQUALS, 0))
            )
        ),
        Rule(
            "rainy-day",
            arrayListOf(
                Condition("rainfall", Operator(OperatorType.GREATER_THAN, 5))
            )
        ),
        Rule(
            "cold-day",
            arrayListOf(
                Condition("temperature", Operator(OperatorType.LESS_THAN, 30)),
                Condition("snow", Operator(OperatorType.GREATER_THAN, 0))
            )
        )
    )
)

val evaluationResult =
    engine.evaluate(
        hashMapOf(
            "temperature" to 50,
            "rainfall" to 0,
            "snow" to 0,
            "uv" to 10,
            "aqi" to 30
        )
    )


// evaluationResult
// [sunny-day -> FAILURE: false, rainy-day -> FAILURE: false, cold-day -> FAILURE: false]
```
