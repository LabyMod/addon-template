import net.labymod.gradle.core.dsl.getClientRepository

plugins {
    id("java-library")
    id("net.labymod.gradle")
    id("net.labymod.gradle.addon")
}

allprojects {
    group = "org.example"
    version = "0.1.0"
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

labyMod {
    defaultPackageName = "org.example" //change this to your main package name (used by all modules)
    addonInfo {
        namespace = "example"
        displayName = "ExampleAddon"
        author = "Example Author"
        description = "Example Description"
        minecraftVersion = "*"
        version = System.getenv().getOrDefault("VERSION", project.version.toString())
    }

    minecraft {
        registerVersions(
                "1.8.9",
                "1.12.2",
                "1.16.5",
                "1.17.1",
                "1.18.2",
                "1.19.2",
                "1.19.3",
                "1.19.4"
        ) { version, provider ->
            configureRun(provider, version)
        }

        subprojects.forEach {
            if (it.name != "game-runner") {
                filter(it.name)
            }
        }
    }

    addonDev {
        snapshotRelease()
    }
}

subprojects {
    plugins.apply("java-library")
    plugins.apply("net.labymod.gradle")
    plugins.apply("net.labymod.gradle.addon")

    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()

        options.encoding = "UTF-8"
    }

    repositories {
        maven("https://libraries.minecraft.net/")
        maven("https://repo.spongepowered.org/repository/maven-public/")
    }
}

fun configureRun(provider: net.labymod.gradle.core.minecraft.provider.VersionProvider, gameVersion: String) {
    provider.runConfiguration {
        val obfuscatedClientJar = getClientRepository(gameVersion).resolve("client-$gameVersion-obfuscated.jar")

        mainClass = "net.minecraft.launchwrapper.Launch"
        jvmArgs("-Dnet.labymod.running-version=${gameVersion}")
        jvmArgs("-Dnet.labymod.obfuscated-jar-path=${obfuscatedClientJar.toAbsolutePath()}")
        jvmArgs("-Dmixin.debug=true")
        jvmArgs("-Dnet.labymod.debugging.all=true")

        args("--tweakClass", "net.labymod.core.loader.vanilla.launchwrapper.Java17LabyModLaunchWrapperTweaker")
        args("--labymod-dev-environment", "true")
        args("--addon-dev-environment", "true")
    }

    provider.javaVersion = JavaVersion.VERSION_17

    provider.mixin {
        val mixinMinVersion = when (gameVersion) {
            "1.8.9", "1.12.2", "1.16.5" -> {
                "0.6.6"
            }

            else -> {
                "0.8.2"
            }
        }

        minVersion = mixinMinVersion
    }
}
