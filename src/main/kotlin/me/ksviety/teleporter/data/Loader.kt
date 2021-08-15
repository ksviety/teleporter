package me.ksviety.teleporter.data

interface Loader<out T> {
    fun load(): T
}
