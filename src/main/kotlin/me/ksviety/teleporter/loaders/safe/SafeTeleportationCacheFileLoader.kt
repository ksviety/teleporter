package me.ksviety.teleporter.loaders.safe

import me.ksviety.teleporter.cache.TeleportationCache
import me.ksviety.teleporter.loaders.TeleportationCacheFileLoader
import me.ksviety.teleporter.savers.TeleportationCacheFileSaver
import java.io.File

class SafeTeleportationCacheFileLoader(file: File) : SafeFileLoader<TeleportationCache>(file, TeleportationCacheFileSaver(file)) {
    private val loader = TeleportationCacheFileLoader(file)

    override fun loadFromContent(content: ByteArray) = loader.load()

    override fun getDefaultContent() = TeleportationCache(emptySet())
}
