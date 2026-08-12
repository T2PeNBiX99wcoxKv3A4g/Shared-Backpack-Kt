package io.github.ykysnk.sharedBackpackKt.config

import io.github.ykysnk.sharedBackpackKt.Utils
import kotlinx.serialization.decodeFromString
import net.mamoe.yamlkt.Yaml
import java.nio.file.Files
import kotlin.io.path.readText
import kotlin.io.path.writeText

object ConfigManager {
    init {
        load()
    }
    private val dataPath = Utils.ConfigDir.resolve("config.yaml")

    var config: Config = Config()
        private set

    fun load() {
        if (!Files.exists(dataPath)) {
            save()
            return
        }
        config = Yaml.decodeFromString<Config>(dataPath.readText())
    }

    fun save() {
        val data = Yaml.encodeToString(config)
        dataPath.writeText(data)
    }
}