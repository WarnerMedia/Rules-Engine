plugins {
    `kotlin-library-conventions`
    `kotlin-dokka-conventions`
    kotlin("jvm")
    id("org.jetbrains.dokka")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "rulesengine-jsonadapter"
            from(components["java"])
        }
    }
}
