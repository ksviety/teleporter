package me.ksviety.teleporter.data

interface Saver<in T> {
    fun save(obj: T)
}
