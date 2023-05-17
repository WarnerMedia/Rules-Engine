import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val packageGroupId: String by project
val packageVersionId: String by project

group = packageGroupId
version = packageVersionId

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish`
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.1")
    testImplementation(kotlin("test"))
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/warnermedia/rules-engine")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            artifactId = "rulesengine-jsonadapter"
            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
