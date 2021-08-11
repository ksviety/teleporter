package me.ksviety.teleporter.savers

import me.ksviety.teleporter.cache.TeleportationCache
import java.net.URI

class TeleportationCacheFileSaver(path: URI) : FileSaver<TeleportationCache>(path) {

    override fun textFromObject(obj: TeleportationCache): String {
        return obj.alreadyTeleportedPlayers.joinToString("\n")
    }
}
