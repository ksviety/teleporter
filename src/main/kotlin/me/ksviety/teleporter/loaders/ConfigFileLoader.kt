package me.ksviety.teleporter.loaders

import com.google.gson.JsonParser
import me.ksviety.teleporter.Config
import java.net.URI

class ConfigFileLoader(path: URI) : FileLoader<Config>(path) {

    override fun loadFromContent(content: String): Config {
        val json = JsonParser().parse(content).asJsonObject

        val centerX = json.get("centerX").asInt
        val centerZ = json.get("centerZ").asInt
        val size = json.get("size").asInt
        val bannedBlocks = json.get("bannedBlocks").asJsonArray.map { it.asString }.toSet()
        val shiftRadius = json.get("shiftRadius").asInt
        val searchIterationsLimit = json.get("searchIterationsLimit").asInt

        return Config(bannedBlocks, centerX, centerZ, size, shiftRadius, searchIterationsLimit)
    }
}
