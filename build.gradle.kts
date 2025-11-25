plugins {
    id("com.gradle.plugin-publish") version "1.2.1"
    `kotlin-dsl`
    `maven-publish`
}

group = "io.bluecave"
version = "0.1.6"

gradlePlugin {
    website = "https://bluecave.io"
    vcsUrl = "https://github.com/bluecave-toolbox/bluecave-gradle-plugin"
    plugins {
        create("blueCave") {
            id = "io.bluecave.plugin"
            displayName = "Blue Cave's Gradle Plugin"
            description = "Fast and safe, at monorepo scale. Static analysis and coverage for every codebase."
            tags = listOf("static-analysis", "coverage", "code-coverage", "code-security", "quality", "code-quality")
            implementationClass = "io.bluecave.plugin.BlueCaveReportPlugin"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
}


