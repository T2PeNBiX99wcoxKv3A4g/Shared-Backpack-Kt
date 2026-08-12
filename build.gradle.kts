import groovy.json.JsonSlurper
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.4.10"
    kotlin("plugin.serialization") version "2.4.10"
    id("fabric-loom") version "1.17-SNAPSHOT"
    id("maven-publish")
}

version = providers.gradleProperty("mod_version").get()
group = providers.gradleProperty("maven_group").get()

base {
    archivesName.set("${property("archives_base_name")}-${property("minecraft_version")}")
}

val targetJavaVersion = 23
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

loom {
    mods {
        register("shared-backpack-kt") {
            sourceSet("main")
        }
    }
    accessWidenerPath = file("src/main/resources/shared-backpack-kt.accesswidener")
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
    mavenCentral()
    maven("https://maven.isxander.dev/releases") {
        name = "Xander Maven"
    }
    maven("https://maven.terraformersmc.com/") {
        name = "Terraformers"
    }
    maven("https://maven.parchmentmc.org") {
        name = "Parchment"
    }
}

dependencies {
    // To change the versions, see the gradle.properties file
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings(
        loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-${property("minecraft_version")}:${property("parchment_mappings")}@zip")
        }
    )
//    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${property("kotlin_loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

    modImplementation("dev.isxander:yet-another-config-lib:${property("yacl_version")}")
    modImplementation("com.terraformersmc:modmenu:${property("modmenu_version")}")
    include(implementation("net.mamoe.yamlkt:yamlkt:${property("yamlkt_version")}")!!)
}

val generateFallbackTranslations = tasks.register("generateFallbackTranslations") {
    description = "Generate fallback translations from en_us.json"

    val inputFile = file(
        "src/main/resources/assets/shared-backpack-kt/lang/en_us.json"
    )

    val outputDir = layout.buildDirectory.dir(
        "generated/sources/fallbackTranslations"
    )

    inputs.file(inputFile)
    outputs.dir(outputDir)

    doLast {
        val outputDirectory = outputDir.get().asFile

        val translations = JsonSlurper()
            .parse(inputFile) as Map<*, *>

        fun toConstantName(key: String): String =
            key
                .replace(Regex("[^A-Za-z0-9]+"), "_")
                .uppercase()
                .trim('_')

        fun escapeKotlinString(value: String): String =
            value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("\t", "\\t")

        val output = buildString {
            appendLine("// Generated file. DO NOT EDIT.")
            appendLine("// Generated from assets/shared-backpack-kt/lang/en_us.json")
            appendLine()
            appendLine("package io.github.ykysnk.sharedBackpackKt")
            appendLine()
            appendLine("object FallbackTranslations {")

            translations.forEach { (key, value) ->
                require(key is String) {
                    "Translation key must be a string: $key"
                }

                require(value is String) {
                    "Translation value must be a string: $key"
                }

                val constantName = toConstantName(key)
                val text = escapeKotlinString(value)

                appendLine("    const val $constantName = \"$text\"")
            }

            appendLine("}")
        }

        val outputFile = outputDirectory.resolve(
            "io/github/ykysnk/sharedBackpackKt/FallbackTranslations.kt"
        )

        outputFile.parentFile.mkdirs()
        outputFile.writeText(output)
    }
}

val generateUpsideDownTranslation = tasks.register("generateUpsideDownTranslation") {
    description = "Generate the en_ud (Upside-Down English) translation from en_us."

    val inputFile =
        file("src/main/resources/assets/shared-backpack-kt/lang/en_us.json")

    val outputDir =
        layout.buildDirectory.dir("generated/resources/upsideDownTranslations")

    inputs.file(inputFile)
    outputs.dir(outputDir)

    doLast {
        val outputDirectory = outputDir.get().asFile
        outputDirectory.mkdirs()

        val translations =
            JsonSlurper().parse(inputFile) as Map<*, *>

        /*
         * Minecraft format placeholders.
         *
         * Examples:
         * %s
         * %d
         * %1$s
         * %2$d
         * %1.2f
         */
        val placeholderRegex =
            Regex("%(?:\\d+\\$)?(?:\\d+)?(?:\\.\\d+)?[a-zA-Z]")

        val upsideDownMap = mapOf(
            // Lowercase
            'a' to 'ɐ',
            'b' to 'q',
            'c' to 'ɔ',
            'd' to 'p',
            'e' to 'ǝ',
            'f' to 'ɟ',
            'g' to 'ƃ',
            'h' to 'ɥ',
            'i' to 'ᴉ',
            'j' to 'ɾ',
            'k' to 'ʞ',
            'l' to 'ן',
            'm' to 'ɯ',
            'n' to 'u',
            'o' to 'o',
            'p' to 'd',
            'q' to 'b',
            'r' to 'ɹ',
            's' to 's',
            't' to 'ʇ',
            'u' to 'n',
            'v' to 'ʌ',
            'w' to 'ʍ',
            'x' to 'x',
            'y' to 'ʎ',
            'z' to 'z',

            // Uppercase
            'A' to '∀',
            'B' to 'B',
            'C' to 'Ɔ',
            'D' to '◖',
            'E' to 'Ǝ',
            'F' to 'Ⅎ',
            'G' to 'פ',
            'H' to 'H',
            'I' to 'I',
            'J' to 'ſ',
            'K' to 'ʞ',
            'L' to '˥',
            'M' to 'M',
            'N' to 'N',
            'O' to 'O',
            'P' to 'Ԁ',
            'Q' to 'Ό',
            'R' to 'ᴚ',
            'S' to 'S',
            'T' to '┴',
            'U' to '∩',
            'V' to 'Λ',
            'W' to 'M',
            'X' to 'X',
            'Y' to '⅄',
            'Z' to 'Z',

            // Punctuation
            '.' to '˙',
            ',' to '\'',
            '\'' to ',',
            '?' to '¿',
            '!' to '¡',
            '[' to ']',
            ']' to '[',
            '(' to ')',
            ')' to '(',
            '<' to '>',
            '>' to '<'
        )

        fun transformText(text: String): String {
            val matches = placeholderRegex.findAll(text).toList()

            // No placeholders.
            if (matches.isEmpty()) {
                return text
                    .reversed()
                    .map { upsideDownMap[it] ?: it }
                    .joinToString("")
            }

            val result = StringBuilder()

            /*
             * We process the text from right to left.
             *
             * Placeholders themselves are kept unchanged.
             */
            var end = text.length

            for (match in matches.asReversed()) {
                // Text after the placeholder.
                result.append(
                    text.substring(match.range.last + 1, end)
                        .reversed()
                        .map { upsideDownMap[it] ?: it }
                        .joinToString("")
                )

                // Placeholder itself.
                result.append(match.value)

                end = match.range.first
            }

            // Text before the first placeholder.
            result.append(
                text.substring(0, end)
                    .reversed()
                    .map { upsideDownMap[it] ?: it }
                    .joinToString("")
            )

            return result.toString()
        }

        val output = buildString {
            appendLine("{")

            translations.entries.forEachIndexed { index, (key, value) ->
                val translated =
                    transformText(value.toString())
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                        .replace("\t", "\\t")

                append("  ")
                append("\"")
                append(key)
                append("\": \"")
                append(translated)
                append("\"")

                if (index != translations.size - 1) {
                    append(",")
                }

                appendLine()
            }

            appendLine("}")
        }

        outputDirectory
            .resolve("assets/shared-backpack-kt/lang/en_ud.json")
            .apply {
                parentFile.mkdirs()
                writeText(output)
            }
    }
}

tasks.processResources {
    inputs.property("version", version)
    inputs.property("minecraft_version", project.property("minecraft_version"))
    inputs.property("loader_version", project.property("loader_version"))
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to version,
            "minecraft_version" to project.property("minecraft_version").toString(),
            "loader_version" to project.property("loader_version").toString(),
            "kotlin_loader_version" to project.property("kotlin_loader_version").toString(),
            "yacl_version" to project.property("yacl_version").toString(),
            "modmenu_version" to project.property("modmenu_version").toString()
        )
    }

    dependsOn(generateUpsideDownTranslation)
    from(
        layout.buildDirectory.dir(
            "generated/resources/upsideDownTranslations"
        )
    )
}

kotlin {
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

tasks.named<KotlinCompile>("compileKotlin") {
    dependsOn(generateFallbackTranslations)
}

tasks.withType<JavaCompile>().configureEach {
    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    // If Javadoc is generated, this must be specified in that task too.
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${base.archivesName}" }
    }
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "${property("archives_base_name")}-${property("minecraft_version")}"
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
