package me.ksviety.teleporter.loaders.safe

import me.ksviety.teleporter.loaders.FileLoader
import me.ksviety.teleporter.savers.FileSaver
import java.io.File
import java.io.FileNotFoundException

abstract class SafeFileLoader<out T>(private val file: File, private val saver: FileSaver<T>) : FileLoader<T>(file) {
    protected abstract fun getDefaultContent(): T

    override fun load(): T {
        return try {
            super.load()
        } catch (e: FileNotFoundException) {
            file.createNewFile()
            saver.save(getDefaultContent())

            load()
        }
    }
}
