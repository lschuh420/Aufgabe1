
plugins {
    id("org.springframework.boot") version "3.1.2" // Specify the Spring Boot version
    id("io.spring.dependency-management") version "1.1.0" // Dependency management plugin
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.example.cloudusageaggregator"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter") // Spring Boot Starter
    implementation("com.google.code.gson:gson:2.11.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// Create a shadow JAR with all dependencies
tasks {
    shadowJar {
        archiveBaseName.set("app")
        archiveClassifier.set("")
        archiveVersion.set(version.toString())
        manifest {
            attributes["Main-Class"] = "com.example.cloudusageaggregator.Main"
        }
    }
}
