package me.ksviety.teleporter.data.savers

import me.ksviety.teleporter.Cache
import java.io.File

class CacheFileSaver(file: File) : FileSaver<Cache>(file) {

    override fun textFromContent(content: Cache): String {
        return content.alreadyTeleportedPlayers.joinToString("\n")
    }
}
