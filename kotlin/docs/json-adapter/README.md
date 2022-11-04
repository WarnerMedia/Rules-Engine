# Examples

Assuming `com.warnermedia.rulesengine:rulesengine-jsonadapter` is installed

## Store engine to JSON file

```kotlin
import com.warnermedia.rulesengine.core.*
import com.warnermedia.rulesengine.jsonadapter.Adapter

fun persistEngine() {
    val engine = Engine(
        "day-type-engine", arrayListOf(
            ...
        )
    )
    Adapter.saveToFile(engine, "engine.json")
}
```

## Read stored engine instance from JSON

```kotlin
import com.warnermedia.rulesengine.jsonadapter.Adapter

val engine = Adapter.readFromFile("engine.json")
```
