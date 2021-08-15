package me.ksviety.teleporter.data.savers

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import me.ksviety.teleporter.Cache
import java.io.File

class CacheFileSaver(file: File) : FileSaver<Cache>(file) {

    override fun textFromContent(content: Cache): String {
        return Gson().toJson(
            JsonArray().apply {
                content.alreadyTeleportedPlayers.map {
                    JsonObject().apply {
                        addProperty("player", it.key)
                        add("position", JsonObject().apply {
                            addProperty("x", it.value.x)
                            addProperty("y", it.value.y)
                            addProperty("z", it.value.z)
                        })
                    }
                }.forEach(::add)
            }
        )
    }
}
