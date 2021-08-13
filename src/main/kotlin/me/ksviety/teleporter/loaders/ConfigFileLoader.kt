package me.ksviety.teleporter.loaders

import com.google.gson.JsonParser
import me.ksviety.teleporter.Config
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

        return Config(bannedBlocks, centerX, centerZ, size, shiftRadius, searchIterationsLimit)
    }
}
