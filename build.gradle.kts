import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.fabricmc.fabric-loom-remap")
    `maven-publish`
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("io.github.ykysnk.translation-generator")
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
    maven("https://maven.fzzyhmstrs.me/") {
        name = "FzzyMaven"
    }
    maven("https://maven.terraformersmc.com/") {
        name = "Terraformers"
    }
    maven("https://maven.parchmentmc.org") {
        name = "Parchment"
    }
    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven") {
                name = "Modrinth"
            }
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
}

loom {
    mods {
        register(providers.gradleProperty("mod_id").get()) {
            sourceSet(sourceSets.main.get())
        }
    }

    val aw = file("src/main/resources/${providers.gradleProperty("mod_id").get()}.aw")
    if (aw.exists())
        accessWidenerPath = aw
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")
    mappings(
        loom.layered {
            officialMojangMappings()
            parchment(
                "org.parchmentmc.data:parchment-${
                    providers.gradleProperty("minecraft_version").get()
                }:${providers.gradleProperty("parchment_mappings").get()}@zip"
            )
        }
    )
    modImplementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${providers.gradleProperty("fabric_kotlin_version").get()}")
    modImplementation("com.terraformersmc:modmenu:${providers.gradleProperty("mod_menu_version").get()}")
    modImplementation("me.fzzyhmstrs:fzzy_config:${providers.gradleProperty("fzzy_config_version").get()}")
}

tasks.processResources {
    val version = version
    inputs.property("version", version)
    inputs.property("minecraft_version", providers.gradleProperty("minecraft_version").get())
    inputs.property("loader_version", providers.gradleProperty("loader_version").get())
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to version,
            "mod_id" to providers.gradleProperty("mod_id").get(),
            "mod_name" to providers.gradleProperty("mod_name").get(),
            "minecraft_version" to providers.gradleProperty("minecraft_version").get(),
            "loader_version" to providers.gradleProperty("loader_version").get(),
            "fabric_kotlin_version" to providers.gradleProperty("fabric_kotlin_version").get(),
            "fabric_api_version" to providers.gradleProperty("fabric_api_version").get(),
            "mod_menu_version" to providers.gradleProperty("mod_menu_version").get(),
            "fzzy_config_version" to providers.gradleProperty("fzzy_config_version").get(),
        )
    }

    dependsOn("generateUpsideDownTranslation")
    from(
        layout.buildDirectory.dir(
            "generated/resources/upsideDownTranslations"
        )
    )
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }

    sourceSets {
        main {
            kotlin.srcDir(
                layout.buildDirectory.dir(
                    "generated/sources/fallbackTranslations"
                )
            )
        }
    }
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.jar {
    val projectName = project.name
    inputs.property("projectName", projectName)

    from("LICENSE") {
        rename { "${it}_$projectName" }
    }
}

// configure the maven publication
publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}

tasks.named<KotlinCompile>("compileKotlin") {
    dependsOn("generateFallbackTranslations")
}

tasks.named<Jar>("sourcesJar") {
    dependsOn("generateFallbackTranslations")
}

loom {
    runs {
        configureEach {
            vmArg("-Dsodium.checks.issue2561=false")
        }
    }
}

translationGenerator {
    modId.set(providers.gradleProperty("mod_id"))
    packageName.set(providers.gradleProperty("package_group"))
}