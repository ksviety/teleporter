package me.ksviety.teleporter.savers

import com.google.gson.*
import me.ksviety.teleporter.Config
import java.io.File

class ConfigFileSaver(file: File) : FileSaver<Config>(file) {
    override fun textFromContent(content: Config): String {
        val json = JsonObject().apply {
            addProperty("centerX", content.centerX)
            addProperty("centerZ", content.centerZ)
            addProperty("size", content.size)
            addProperty("shiftRadius", content.shiftRadius)
            addProperty("searchIterationsLimit", content.searchIterationsLimit)
            add("bannedBlocks", JsonArray().apply { content.getBannedBlocks().forEach(this::add) })
        }

        return Gson().toJson(json as JsonElement)
    }
}
