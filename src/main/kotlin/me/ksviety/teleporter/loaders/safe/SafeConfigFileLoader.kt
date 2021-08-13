package me.ksviety.teleporter.loaders.safe

import me.ksviety.teleporter.Config
import me.ksviety.teleporter.loaders.ConfigFileLoader
import me.ksviety.teleporter.savers.ConfigFileSaver
import java.io.File

class SafeConfigFileLoader(file: File) : SafeFileLoader<Config>(file, ConfigFileSaver(file)) {
    private val loader = ConfigFileLoader(file)

    override fun loadFromContent(content: ByteArray) = loader.load()

    override fun getDefaultContent() = Config.default
}
