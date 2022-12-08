rootProject.name = "rulesengine"
include("core", "json-adapter")

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version("1.7.21")
        id("org.jetbrains.dokka") version("1.7.20")
    }
}
