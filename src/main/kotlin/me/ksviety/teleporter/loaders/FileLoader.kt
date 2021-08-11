package me.ksviety.teleporter.loaders

import me.ksviety.teleporter.Loader
import java.io.File
import java.net.URI

abstract class FileLoader<T>(path: URI) : Loader<T> {
    private val file = File(path)

    protected abstract fun loadFromContent(content: String) : T

    final override fun load() = loadFromContent(
        file.readText()
    )
}
