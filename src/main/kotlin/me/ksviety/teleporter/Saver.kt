package me.ksviety.teleporter

interface Saver<in T> {
    fun save(obj: T)
}
