package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.exceptions.TeleportationException

interface TeleportationFailHandler<in T> {
    fun handle(target: T, cause: TeleportationException)
}
