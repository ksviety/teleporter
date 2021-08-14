package me.ksviety.teleporter.data.loaders

import me.ksviety.teleporter.cache.TeleportationCache
import java.io.File

class TeleportationCacheFileLoader(file: File) : FileLoader<TeleportationCache>(file) {

    override fun loadFromContent(content: ByteArray): TeleportationCache {
        val players = hashSetOf<String>()

        String(content).lines().forEach(players::add)

        return TeleportationCache(players)
    }
}
