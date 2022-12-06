plugins {
    `kotlin-library-conventions`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "rulesengine-jsonadapter"
            from(components["java"])
        }
    }
}
