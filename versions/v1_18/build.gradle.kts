version = "0.1.0"

plugins {
    id("net.labymod.gradle.vanilla")
    id("net.labymod.gradle.volt")
}

val minecraftGameVersion = "1.18.2"
val minecraftVersionTag: String = "1.18"

version = "1.0.0"

minecraft {
    version(minecraftGameVersion)
    platform(org.spongepowered.gradle.vanilla.repository.MinecraftPlatform.CLIENT)
    runs {
        client {
            requiresAssetsAndNatives.set(true)
            mainClass("net.minecraft.launchwrapper.Launch")
            args("--tweakClass", "net.labymod.core.loader.vanilla.launchwrapper.LabyModLaunchWrapperTweaker")
            args("--labymod-dev-environment", "true")
            args("--addon-dev-environment", "true")
            jvmArgs("-Dnet.labymod.running-version=$minecraftGameVersion")
        }
    }
}

dependencies {
    annotationProcessor("net.labymod:sponge-mixin:0.1.0+0.11.2+mixin.0.8.5")
    labyProcessor()
    labyApi("v1_18")
    api(project(":core"))
}

volt {
    mixin {
        compatibilityLevel = "JAVA_17"
        minVersion = "0.8.2"
    }

    packageName("org.example.addon.v1_18.mixins")

    //Use this if you want to inherit the mixins from your 1.17 implementation
    //packageName("org.example.addon.v1_17.mixins")
    //inheritFrom("v1_17")

    version = minecraftGameVersion
}

//Use this if you want to inherit the code from your 1.17 implementation
//val inheritv117 = sourceSets.create("inherit-v1_17") {
//    java.srcDirs(project.files("../v1_17/src/main/java/"))
//
//    //Use the following if you want to inherit the 1.17 dependent code but not everything is given in your 1.18 code
//    java {
//        exclude("org.example.addon.v1_17.mixins.ExampleMixin")
//    }
//}
//
//sourceSets.getByName("main") {
//    java.srcDirs(inheritv117.java)
//}

intellij {
    minorMinecraftVersion(minecraftVersionTag)
    val javaVersion = project.findProperty("net.labymod.runconfig-v1_18-java-version")

    //Use this if you want to rename your 1.17 dependent code to 1.18
    //renameApiMixin {
    //    relocate("org.example.addon.v1_17.", "org.example.addon.v1_18.")
    //}

    if (javaVersion != null) {
        run {
            javaVersion(javaVersion as String)
        }
    }
}

tasks.collectNatives {
    into("${project.gradle.gradleUserHomeDir}/caches/VanillaGradle/v2/natives/${minecraftGameVersion}/")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}