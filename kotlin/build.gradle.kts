plugins {
    id("org.jetbrains.dokka")
}

repositories {
    mavenCentral()
}

tasks.wrapper {
    gradleVersion = "7.5.1"
    distributionType = Wrapper.DistributionType.BIN
}
