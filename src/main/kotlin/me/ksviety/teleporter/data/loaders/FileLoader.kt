package me.ksviety.teleporter.data.loaders

import me.ksviety.teleporter.data.Loader
import java.io.File

abstract class FileLoader<out T>(private val file: File) : Loader<T> {
    protected abstract fun loadFromContent(content: ByteArray) : T

    override fun load() = loadFromContent(
        file.readBytes()
    )
}
