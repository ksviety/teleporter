package me.ksviety.teleporter.data.repository

import me.ksviety.teleporter.data.Loader
import me.ksviety.teleporter.data.Saver

abstract class Repository<T>(saver: Saver<T>, loader: Loader<T>) : Saver<T> by saver, Loader<T> by loader
