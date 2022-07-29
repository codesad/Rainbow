pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://maven.architectury.dev/")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.minecraftforge.net/")
        maven("https://repo.sk1er.club/repository/maven-releases/")
        maven("https://jitpack.io/")
    }
    plugins {
        id("com.github.johnrengelman.shadow") version "7.+"
        id("gg.essential.loom") version "0.10.+"
        id("dev.architectury.architectury-pack200") version "0.1.3"
        kotlin("jvm") version "1.7.10"
    }
}
rootProject.name = "Rainbow"