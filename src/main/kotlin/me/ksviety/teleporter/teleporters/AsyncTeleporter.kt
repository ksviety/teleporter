package me.ksviety.teleporter.teleporters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import me.ksviety.teleporter.Teleporter
import me.ksviety.teleporter.exceptions.TeleportationException

class AsyncTeleporter<in T>(
    private val scope: CoroutineScope,
    private val teleportationFailHandler: TeleportationFailHandler<T>,
    private val teleporter: Teleporter<T>,
    ) : Teleporter<T> {

    override fun teleport(obj: T) {
        scope.launch {
            try {
                teleporter.teleport(obj)
            } catch (exception: TeleportationException) {
                teleportationFailHandler.handle(obj, exception)
            } finally {
                cancel()
            }
        }
    }
}
