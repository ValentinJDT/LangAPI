plugins {
    id("java")
    id("maven-publish")
}

group = "fr.mrcoq.lib"
version = "1.0.4"

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
            groupId = "fr.mrcoq.lib"
            artifactId = "MultiLanguageAPI"
            version = "1.0.4"

            from(components["java"])
        }
    }
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}