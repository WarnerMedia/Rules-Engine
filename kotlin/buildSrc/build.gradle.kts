import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // This is the first time kotlin is present during the build
    // Not providing the version here, makes `kotlin-dsl` use older version
    // That leads to `jvmTarget` of `17` erroring out
    kotlin("jvm") version "1.7.21"
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
