import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("rulesengine.kotlin-library-conventions")
}

dependencies {
    testImplementation(kotlin("test"))
}

val packageGroupId: String by project
val packageVersionId: String by project

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = packageGroupId
            artifactId = "rulesengine-core"
            version = packageVersionId

            from(components["java"])
        }
    }
}
