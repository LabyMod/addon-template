plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "org.example"
version = providers.environmentVariable("VERSION").getOrElse("1.0.0")

labyMod {
    defaultPackageName = "org.example" //change this to your main package name (used by all modules)

    minecraft {
        registerVersion(versions.toTypedArray()) {

        }
    }

    addonInfo {
        namespace = "example"
        displayName = "ExampleAddon"
        author = "Example Author"
        description = "Example Description"
        minecraftVersion = "*"
        version = version.toString()
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}