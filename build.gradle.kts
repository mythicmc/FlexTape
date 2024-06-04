import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    kotlin("jvm") version "2.0.0"
    kotlin("kapt") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.kyori.blossom") version "2.0.0"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7" // IntelliJ + Blossom integration
    id("org.ajoberstar.grgit.service") version "5.2.0"
}

group = "org.mythicmc"
version = "0.1.0${getVersionMetadata()}"
description = "Flex tape for Velocity proxy migration to fix some oddities"

repositories {
    mavenCentral()
    maven(url = "https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    kapt("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    compileOnly(fileTree(mapOf("dir" to "lib", "includes" to listOf("*.jar"))))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

sourceSets {
    main {
        blossom {
            resources {
                property("version", project.version.toString())
                property("description", project.description)
            }
            javaSources {
                property("version", project.version.toString())
                property("description", project.description)
            }
        }
    }
}

tasks.getByName<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    relocate("kotlin", "org.mythicmc.flextape.kotlin")
}

fun getVersionMetadata(): String {
    if (project.hasProperty("skipVersionMetadata")) return ""

    val grgit = try { grgitService.service.orNull?.grgit } catch (e: Exception) { null }
    if (grgit != null) {
        val head = grgit.head() ?: return "+unknown" // No head, fresh git repo
        var id = head.abbreviatedId
        val tag = grgit.tag.list().find { head.id == it.commit.id }

        // If we're on a tag and the tree is clean, don't put any metadata
        if (tag != null && grgit.status().isClean) {
            return ""
        }
        // Flag the build if the tree isn't clean
        if (!grgit.status().isClean) {
            id += "-dirty"
        }

        return "+git.$id"
    }

    return "+unknown"
}
