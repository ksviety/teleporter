package me.ksviety.teleporter.data.savers

import me.ksviety.teleporter.cache.TeleportationCache
import java.io.File

class TeleportationCacheFileSaver(file: File) : FileSaver<TeleportationCache>(file) {

    override fun textFromContent(content: TeleportationCache): String {
        return content.alreadyTeleportedPlayers.joinToString("\n")
    }
}
