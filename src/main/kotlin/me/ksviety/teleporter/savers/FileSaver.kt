package me.ksviety.teleporter.savers

import me.ksviety.teleporter.Saver
import java.io.File
import java.io.FileWriter
import java.net.URI

abstract class FileSaver<T>(private val path: URI) : Saver<T> {

    protected abstract fun textFromObject(obj: T): String

    final override fun save(obj: T) {
        FileWriter(
            File(path)
        ).apply {
            write(textFromObject(obj))
            flush()
            close()
        }
    }
}
