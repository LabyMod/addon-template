version = "0.1.0"

plugins {
    id("net.labymod.gradle.legacyminecraft")
    id("net.labymod.gradle.volt")
}

val minecraftGameVersion: String = "1.8.9"
val minecraftVersionTag: String = "1.8"

dependencies {
    annotationProcessor("net.labymod:sponge-mixin:0.1.0+0.11.2+mixin.0.8.5")
    labyProcessor()
    labyApi("v1_8")
    api(project(":core"))
}

legacyMinecraft {
    version(minecraftGameVersion)

    mainClass("net.minecraft.launchwrapper.Launch")
    args("--tweakClass", "net.labymod.core.loader.vanilla.launchwrapper.LabyModLaunchWrapperTweaker")
    args("--labymod-dev-environment", "true")
    args("--addon-dev-environment", "true")
    jvmArgs("-Dnet.labymod.running-version=$minecraftGameVersion")
}

volt {
    mixin {
        compatibilityLevel = "JAVA_8"
        minVersion = "0.6.6"
    }

    packageName("org.example.addon.v1_8.mixins")

    version = minecraftGameVersion
}

intellij {
    minorMinecraftVersion(minecraftVersionTag)
    val javaVersion = project.findProperty("net.labymod.runconfig-v1_8-java-version")

    if (javaVersion != null) {
        run {
            javaVersion(javaVersion as String)
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

