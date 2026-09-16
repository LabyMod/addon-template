rootProject.name = "labymod4-addon-template"

pluginManagement {
    repositories {
        maven("https://maven.laby.net/api/v1/maven/release/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.fabricmc.net/")
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        id("net.labymod.labygradle.settings") version "0.9.0"
    }
}

plugins {
    id("net.labymod.labygradle.settings")
}

include(":api")
include(":core")
