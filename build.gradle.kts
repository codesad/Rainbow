import dev.architectury.pack200.java.Pack200Adapter
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.task.RemapJarTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
    id("gg.essential.loom")
    id("dev.architectury.architectury-pack200")
    java
    idea
}

version = "1.1.1"
group = "me.sad"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.sk1er.club/repository/maven-public/")
    maven("https://repo.sk1er.club/repository/maven-releases/")
    maven("https://jitpack.io")
}

val include: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")
    include("gg.essential:loader-launchwrapper:1.1.3")
    compileOnly("gg.essential:essential-1.8.9-forge:2581")
    compileOnly("org.spongepowered:mixin:0.8.5")
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
}

loom {
    launchConfigs {
        getByName("client") {
            arg("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
            arg("--mixin", "rainbow.mixins.json")
        }
    }

    forge {
        pack200Provider.set(Pack200Adapter())
        mixinConfig("rainbow.mixins.json")
    }

    mixin {
        defaultRefmapName.set("mixins.rainbow.refmap.json")
    }
}
tasks {
    processResources {
        inputs.property("version", project.version)
        inputs.property("mcversion", "1.8.9")
        filesMatching("mcmod.info") {
            expand(mapOf("version" to project.version, "mcversion" to "1.8.9"))
        }
    }

    named<Jar>("jar") {
        archiveClassifier.set("thin")
        manifest {
            attributes(
                mapOf(
                    "FMLCorePluginContainsFMLMod" to true,
                    "ForceLoadAsMod" to true,
                    "MixinConfigs" to "rainbow.mixins.json",
                    "ModSide" to "CLIENT",
                    "ModType" to "FML",
                    "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker",
                    "TweakOrder" to "0"
                )
            )
        }
    }

    named<ShadowJar>("shadowJar") {
        archiveClassifier.set("dev")
        archiveBaseName.set("Rainbow")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        configurations = listOf(include)
    }

    named<RemapJarTask>("remapJar") {
        input.set(shadowJar.get().archiveFile)
    }

    withType<JavaCompile>() {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
        options.encoding = "UTF-8"
        options.release.set(8)
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}