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
    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-tensorflow:0.5.1")
    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-onnx:0.5.1")
    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-visualization:0.5.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}
