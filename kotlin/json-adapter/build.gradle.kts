plugins {
    id("rulesengine.kotlin-library-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
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
