plugins {
    id("java")
    id("maven-publish")
}

val projectVersion = "1.1.0"
val projectGroup = "fr.mrcoq.lib"

group = projectGroup
version = projectVersion

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
    publications {
        create<MavenPublication>("maven") {
            groupId = projectGroup
            artifactId = rootProject.name
            version = projectVersion

            from(components["java"])
        }
    }
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}