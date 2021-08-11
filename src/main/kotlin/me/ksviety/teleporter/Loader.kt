package me.ksviety.teleporter

interface Loader<T> {
    fun load(): T
}
