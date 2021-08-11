package me.ksviety.teleporter.loaders

import me.ksviety.teleporter.Config
import org.json.JSONObject
import java.net.URI
import java.util.HashSet

class ConfigFileLoader(path: URI) : FileLoader<Config>(path) {

    override fun loadFromContent(content: String): Config {
        val json = JSONObject(content)

        val centerX = json.getInt("centerX")
        val centerZ = json.getInt("centerZ")
        val size = json.getInt("size")
        val bannedBlocksArray = json.getJSONArray("bannedBlocks")
        val shiftRadius = json.getInt("shiftRadius")
        val searchIterationsLimit = json.getInt("searchIterationsLimit")
        val bannedBlocks: MutableSet<String> = HashSet()

        for (i in bannedBlocksArray.toList().indices) {
            bannedBlocks.add(bannedBlocksArray.getString(i))
        }

        return Config(bannedBlocks, centerX, centerZ, size, shiftRadius, searchIterationsLimit)
    }
}
