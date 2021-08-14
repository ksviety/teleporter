package me.ksviety.teleporter

interface Teleporter<in T> {
    fun teleport(obj: T)
}
