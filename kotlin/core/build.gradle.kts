plugins {
    `kotlin-library-conventions`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "rulesengine-core"
            from(components["java"])
        }
    }
}
