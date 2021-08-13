package me.ksviety.teleporter.loaders.safe

import me.ksviety.teleporter.loaders.FileLoader
import me.ksviety.teleporter.savers.FileSaver
import java.io.File

abstract class SafeFileLoader<out T>(private val file: File, private val saver: FileSaver<T>) : FileLoader<T>(file) {
    protected abstract fun getDefaultContent(): T

    override fun load(): T {
        if (!file.exists()) {
            file.createNewFile()
            saver.save(getDefaultContent())
        }

        return super.load()
    }
}
