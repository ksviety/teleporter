package me.ksviety.teleporter

import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.HashSet
import java.util.stream.Collectors

class Config private constructor(
    private val bannedBlocks: Set<String>,
    val centerX: Int,
    val centerZ: Int,
    val size: Int,
    val shiftRadius: Int,
    val searchIterationsLimit: Int
) {
    fun getBannedBlocks(): Collection<String> {
        return bannedBlocks
    }

    companion object {

        fun fromFile(file: File): Config {
            val reader = BufferedReader(FileReader(file))
            val json = reader.lines().collect(Collectors.joining())
            val config = JSONObject(json)

            val centerX = config.getInt("centerX")
            val centerZ = config.getInt("centerZ")
            val size = config.getInt("size")
            val bannedBlocksArray = config.getJSONArray("bannedBlocks")
            val shiftRadius = config.getInt("shiftRadius")
            val searchIterationsLimit = config.getInt("searchIterationsLimit")
            val bannedBlocks: MutableSet<String> = HashSet()

            for (i in bannedBlocksArray.toList().indices) {
                bannedBlocks.add(bannedBlocksArray.getString(i))
            }

            return Config(bannedBlocks, centerX, centerZ, size, shiftRadius, searchIterationsLimit)
        }
    }
}
