buildscript {
    repositories {
        var bearerToken = System.getenv("LABYMOD_BEARER_TOKEN")

         if (bearerToken == null && env.TOKEN.isPresent) {
            bearerToken = env.TOKEN.value
        }

        maven("https://dist.labymod.net/api/v1/maven/release/") {
            name = "LabyMod Distributor"

            authentication {
                create<HttpHeaderAuthentication>("header")
            }

            credentials(HttpHeaderCredentials::class) {
                name = "Authorization"
                value = "Bearer $bearerToken"
            }
        }


        maven("https://repo.spongepowered.org/repository/maven-public") {
            name = "SpongePowered Repository"
        }

        mavenLocal()
    }

    dependencies {
        classpath("net.labymod.gradle", "addon", "0.2.56")
    }
}

plugins {
    id("java-library")
    id("co.uzzu.dotenv.gradle") version "2.0.0"
}

group = "org.example"
version = "1.0.0"

plugins.apply("net.labymod.gradle.addon")

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

subprojects {
    plugins.apply("java-library")
    plugins.apply("net.labymod.gradle.addon")

    repositories {
        maven("https://libraries.minecraft.net/")
        maven("https://repo.spongepowered.org/repository/maven-public/")
        mavenLocal()
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
    }
}

addon {
    addonInfo {
        namespace("example")
        displayName("Example Addon")
        author("Example Author")
        description("An Example Description!")
        version(System.getenv().getOrDefault("VERSION", "0.0.0"))

        //if you want to add dependencies, go to the build.gradle.kts in the core or api module
        //add take a look in the dependencies block
    }

    snapshotRelease()

}
