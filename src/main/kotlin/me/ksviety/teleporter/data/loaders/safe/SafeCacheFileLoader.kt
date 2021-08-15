package me.ksviety.teleporter.data.loaders.safe

import me.ksviety.teleporter.Cache
import me.ksviety.teleporter.data.loaders.CacheFileLoader
import me.ksviety.teleporter.data.savers.CacheFileSaver
import java.io.File

class SafeCacheFileLoader(file: File) : SafeFileLoader<Cache>(file, CacheFileSaver(file)) {
    private val loader = CacheFileLoader(file)

    override fun loadFromContent(content: ByteArray) = loader.load()

    override fun getDefaultContent() = Cache(emptyMap())
}
