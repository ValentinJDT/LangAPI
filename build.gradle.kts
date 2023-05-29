plugins {
    id("java")
    `maven-publish`
}

group = "fr.mrcoq.lib"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ValentinJDT/MultiLanguageAPI")
            credentials {
                username = System.getenv("ACTOR")
                password = System.getenv("TOKEN")
            }
        }
    }
}