# Rules-Engine

Next generation rules engine - `Rules-Engine` is written in Kotlin.

## Working with the repo

### Setup

Requires:
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Kotlin 1.7.20](https://kotlinlang.org/docs/command-line.html)
- [Gradle 7.4.2](https://gradle.org/install/)

### Relevant commands

Build the library

```bash
$ ./gradlew build
```

## Installing the package(s)

Follow the steps mentioned in the [parent README](https://github.com/WarnerMedia/Rules-Engine#using-the-implementations)
first. 

If using Gradle (kotlin DSL), add the following to the `repositories`:

```kts
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/warnermedia/rules-engine")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
```

Then, add the following to `dependencies`:

```kotlin
    implementation("com.warnermedia.rulesengine:rulesengine-core:<version>")
```

To allow for JSON persistence capability, add the following in addition to the above:

```kotlin
    implementation("com.warnermedia.rulesengine:rulesengine-jsonadapter:<version>")
```

For more examples, look [here](https://github.com/WarnerMedia/Rules-Engine/tree/main/kotlin/docs/examples)

## Initial Contributors

- [Satvik Shukla](https://github.com/satvik-s)
