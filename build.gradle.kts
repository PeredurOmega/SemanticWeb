plugins {
    kotlin("js") version "1.6.10"
    id("com.github.turansky.kfc.webpack") version "4.61.0"
}

group = "fr.insa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

dependencies {
    testImplementation(kotlin("test"))
    implementation(enforcedPlatform(kotlinw("wrappers-bom:0.0.1-pre.290-kotlin-1.6.10")))

    implementation(kotlinw("react"))
    implementation(kotlinw("react-dom"))
    implementation(kotlinw("react-router-dom"))

    implementation(kotlinw("mui"))
    implementation(kotlinw("mui-icons"))
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