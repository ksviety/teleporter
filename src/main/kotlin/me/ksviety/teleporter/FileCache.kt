package me.ksviety.teleporter

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.minecraft.util.math.Vec3i
import java.io.*

class FileCache(private val file: File) : Cache {
    private val players: MutableMap<String, Vec3i> = JsonParser()
        .parse(FileReader(file))
        .asJsonArray
        .map { it.asJsonObject }
        .associate {
            val player = it.get("player").asString
            val spawn = with(it.get("spawn").asJsonObject) {
                Vec3i(
                    get("x").asInt,
                    get("y").asInt,
                    get("z").asInt
                )
            }

            player to spawn
        }
        .toMutableMap()

    override fun readPlayers() = players

    override fun recordPlayer(name: String, spawn: Vec3i) {
        players[name] = spawn
    }

    override operator fun contains(name: String) = players.containsKey(name)

    override fun save() {
        FileWriter(
            file
        ).write(
            Gson().toJson(
                JsonArray().apply {
                    for ((player, spawn) in players) {
                        add(
                            JsonObject().apply {
                                addProperty("player", player)
                                add(
                                    "spawn",
                                    JsonObject().apply {
                                        addProperty("x", spawn.x)
                                        addProperty("y", spawn.y)
                                        addProperty("z", spawn.z)
                                    }
                                )
                            }
                        )
                    }
                }
            )
        )
    }
}
