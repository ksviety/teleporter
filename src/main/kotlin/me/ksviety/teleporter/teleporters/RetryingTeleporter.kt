package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.Teleporter
import me.ksviety.teleporter.exceptions.TeleportationException

class RetryingTeleporter<in T>(private val attempts: Int, private val original: Teleporter<T>) : Teleporter<T> {
    override fun teleport(obj: T) {
        teleport(obj, 0)
    }

    private fun teleport(obj: T, attempt: Int) {
        try {
            original.teleport(obj)
        } catch (e: TeleportationException) {
            if (attempt == attempts)
                throw e

            teleport(obj, attempt+1)
        }
    }
}
