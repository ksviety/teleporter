package me.ksviety.teleporter.data.loaders

import com.google.gson.JsonParser
import me.ksviety.teleporter.Cache
import net.minecraft.util.math.Vec3i
import java.io.File

class CacheFileLoader(file: File) : FileLoader<Cache>(file) {

    override fun loadFromContent(content: ByteArray): Cache {
        val json = JsonParser().parse(String(content))

        val cache = hashMapOf<String, Vec3i>()

        json.asJsonArray.forEach {
            val obj = it.asJsonObject
            cache[obj.get("player").asString] = with(obj.get("position").asJsonObject) {
                Vec3i(
                    get("x").asDouble,
                    get("y").asDouble,
                    get("z").asDouble
                )
            }
        }

        return Cache(cache)
    }
}
