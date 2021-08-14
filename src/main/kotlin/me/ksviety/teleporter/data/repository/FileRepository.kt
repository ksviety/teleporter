package me.ksviety.teleporter.data.repository

import me.ksviety.teleporter.data.loaders.FileLoader
import me.ksviety.teleporter.data.savers.FileSaver

abstract class FileRepository<T>(saver: FileSaver<T>, loader: FileLoader<T>) : Repository<T>(saver, loader)
