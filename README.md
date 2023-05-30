# MultiLanguageAPI

MultiLanguageAPI vous permet de gérer automatiquement le choix de traduction de vos messages.

## Langues fonctionnelles

- Français
- Anglais
- Espagnol
- Italien
- Allemand
- Chinois
- Arabe

Des langues peuvent être ajoutées, soumettez les moi !

## Installation

Pour le moment, le projet n'est disponible qu'en utilisant le JAR en tant que dépendance.

### Gradle Kts

Ajoutez le repository :
```kotlin
repositories {
    /* Others repositories */
    maven {
        name = "jitpack.io"
        url = "https://jitpack.io"
    }
}
```

Ajoutez dans la dépendance :
```kotlin
dependencies {
    /* Others dependencies */
    implementation("com.github.ValentinJDT:MultiLanguageAPI:[last-version]")
}
```

Pour compiler l'api dans votre plugin, ajoutez (ou modifiez) la task jar :
```kotlin
jar {
    from {
        configurations
                .runtimeClasspath
                .collect {
                    if (it.name.contains("MultiLanguageAPI")) {
                        zipTree(it)
                    }
                }
    }
}
```

### Maven

Ajoutez ces repositories :
```xml
    <repositories>
        <!-- Other repositories -->
        <repository>
            <id>sonatype</id>
            <url>https://oss.sonatype.org/content/groups/public/</url>
        </repository>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```

Ajoutez la dépendance (Laissez bien le scope sur compile) :
```xml
<dependencies>
    <!-- Other dependencies -->
    <dependency>
        <groupId>com.github.ValentinJDT</groupId>
        <artifactId>MultiLanguageAPI</artifactId>
        <version>[last-version]</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

Ajoutez le plugin suivant :
```xml
<build>
    <plugins>
        <!-- Others plugins... -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-assembly-plugin</artifactId>
            <version>3.1.1</version>
        
            <configuration>
                <descriptorRefs>
                    <descriptorRef>jar-with-dependencies</descriptorRef>
                </descriptorRefs>
            </configuration>
        
            <executions>
                <execution>
                    <id>make-assembly</id>
                    <phase>package</phase>
                    <goals>
                        <goal>single</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

Lorsque vous allez build le projet (`mvn package`), un deuxième JAR va apparaître. Utilisez le jar nommé `<nom>-jar-with-dependencies`.


## Crédit

Ce projet a été inspiré du projet [LangAPI](https://github.com/Sandro642/LangAPI) à [Sandro642](https://github.com/Sandro642).
