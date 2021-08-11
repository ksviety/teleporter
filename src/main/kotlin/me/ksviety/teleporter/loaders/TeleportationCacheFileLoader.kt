package me.ksviety.teleporter.loaders

import me.ksviety.teleporter.cache.TeleportationCache
import java.net.URI

class TeleportationCacheFileLoader(path: URI) : FileLoader<TeleportationCache>(path) {

    override fun loadFromContent(content: String): TeleportationCache {
        val players = hashSetOf<String>()

        content.lines().forEach(players::add)

        return TeleportationCache(players)
    }
}
