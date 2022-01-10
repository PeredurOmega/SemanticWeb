plugins {
    kotlin("js") version "1.6.10"
}

group = "fr.insa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target-pre.290-kotlin-1.6.10"

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlinw("react:17.0.2"))
    implementation(kotlinw("react-dom:17.0.2"))
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}