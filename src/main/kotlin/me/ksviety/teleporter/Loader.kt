package me.ksviety.teleporter

interface Loader<out T> {
    fun load(): T
}
