import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("rulesengine.kotlin-library-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    testImplementation(kotlin("test"))
}

val packageGroupId: String by project
val packageVersionId: String by project

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = packageGroupId
            artifactId = "rulesengine-jsonadapter"
            version = packageVersionId

            from(components["java"])
        }
    }
}
