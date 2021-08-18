package me.ksviety.teleporter.data.loaders

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import me.ksviety.teleporter.Config
import net.minecraft.util.math.Vec3i
import java.io.File

class ConfigFileLoader(file: File) : FileLoader<Config>(file) {

    override fun loadFromContent(content: ByteArray): Config {
        val json = JsonParser().parse(String(content)).asJsonObject

        val centerX = json.get("centerX").asInt
        val centerZ = json.get("centerZ").asInt
        val size = json.get("size").asInt
        val bannedBlocks = json.get("bannedBlocks").asJsonArray.map { it.asString }.toSet()
        val shiftRadius = json.get("shiftRadius").asInt
        val searchIterationsLimit = json.get("searchIterationsLimit").asInt
        val spawn = with(json.get("spawn").asJsonObject) {
            Vec3i(
                get("x").asDouble,
                get("y").asDouble,
                get("z").asDouble
            )
        }

        return Config(bannedBlocks, centerX, centerZ, size, shiftRadius, searchIterationsLimit, spawn)
    }
}
