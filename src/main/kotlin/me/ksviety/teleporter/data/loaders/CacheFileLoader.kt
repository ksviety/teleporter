package me.ksviety.teleporter.data.loaders

import me.ksviety.teleporter.Cache
import java.io.File

class CacheFileLoader(file: File) : FileLoader<Cache>(file) {

    override fun loadFromContent(content: ByteArray): Cache {
        val players = hashSetOf<String>()

        String(content).lines().forEach(players::add)

        return Cache(players)
    }
}
