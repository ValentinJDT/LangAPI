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

### Gradle

Ajoutez dans le JAR dans les dépendances :
```groovy
dependencies {
    compileOnly "org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT"
    implementation files("<path>\\MultiLanguageAPI-1.0.jar")
}
```

Pour compiler l'api dans votre plugin, ajoutez (ou modifiez) la task jar :
```groovy
jar {
    from {
        configurations
                .runtimeClasspath
                .collect {
                    if (it.name.equalsIgnoreCase("MultiLanguageAPI-1.0.jar")) {
                        zipTree(it)
                    }
                }
    }
}
```

### Maven

Ajoutez ce repository :
```xml
    <repositories>
        <!-- Other repositories -->
        <repository>
            <id>sonatype</id>
            <url>https://oss.sonatype.org/content/groups/public/</url>
        </repository>
    </repositories>
```

Ajoutez le plugin Maven Assembly :
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

Importez le JAR dans vos dépendances :
```xml
<dependency>
    <groupId>com.sample</groupId>
    <artifactId>sample</artifactId>
    <version>1.0</version>
    <scope>compile</scope>
    <systemPath>path-to-jar/MultiLanguageAPI-1.0.jar</systemPath>
</dependency>
```



Lorsque vous allez build le projet (`mvn package`), un deuxième jar va apparaître. Utilisez le jar nommé `<nom>-dependencies.jar`.


## Crédit

Ce projet a été inspiré du projet [LangAPI](https://github.com/Sandro642/LangAPI) à [Sandro642](https://github.com/Sandro642).
