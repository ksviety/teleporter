package me.ksviety.teleporter.data.savers

import me.ksviety.teleporter.data.Saver
import java.io.File
import java.io.FileWriter

abstract class FileSaver<T>(private val file: File) : Saver<T> {
    protected abstract fun textFromContent(content: T): String

    final override fun save(content: T) {
        FileWriter(file).apply {
            write(textFromContent(content))
            flush()
            close()
        }
    }
}
