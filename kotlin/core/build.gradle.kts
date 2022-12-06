plugins {
    `kotlin-library-conventions`
    `kotlin-dokka-conventions`
    kotlin("jvm")
    id("org.jetbrains.dokka")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "rulesengine-core"
            from(components["java"])
        }
    }
}
