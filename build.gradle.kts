plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "com.z100.g1turing"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    /* KotlinDL */
    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-tensorflow:0.5.1")
    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-onnx:0.5.1")
    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-visualization:0.5.1")

    /* Logging */
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}
