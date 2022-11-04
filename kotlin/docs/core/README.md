# Examples

Assuming `com.warnermedia.rulesengine:rulesengine-core` is installed

## Basic usage

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
```
