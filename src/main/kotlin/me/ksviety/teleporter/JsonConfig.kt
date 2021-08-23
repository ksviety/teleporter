package me.ksviety.teleporter

import com.google.gson.JsonObject
import net.minecraft.util.math.Vec3i

class JsonConfig(private val json: JsonObject) : Config {

    override fun readBannedBlocks() = json.get("bannedBlocks").asJsonArray.map { it.asString }

    override fun readCenterX() = json.get("centerX").asInt

    override fun readCenterZ() = json.get("centerY").asInt

    override fun readSize() = json.get("size").asInt

    override fun readShiftRadius() = json.get("shiftRadius").asInt

    override fun readSearchIterationsLimit() = json.get("searchIterations").asInt

    override fun readSpawnPosition() = with(json.get("spawn").asJsonObject) {
        Vec3i(
            get("x").asInt,
            get("y").asInt,
            get("z").asInt
        )
    }
}
