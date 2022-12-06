plugins {
    `kotlin-library-conventions`
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
