if (startParameter.taskRequests.find { it.args.contains("assemble") } == null) {
    include("proto", "stub", "grpc-server", "app")
} else {
    include("proto", "stub", "grpc-server")
}

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        google()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "WebRTC"
