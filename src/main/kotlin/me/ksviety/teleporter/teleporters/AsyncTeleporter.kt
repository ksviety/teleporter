package me.ksviety.teleporter.teleporters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.ksviety.teleporter.Teleporter
import me.ksviety.teleporter.exceptions.TeleportationException

abstract class AsyncTeleporter<in T>(
    private val scope: CoroutineScope,
    private val teleporter: Teleporter<T>,
    ) : Teleporter<T> {

    protected abstract fun onTeleportationFailed(target: T, cause: TeleportationException)

    final override fun teleport(obj: T) {
        scope.launch {
            try {
                teleporter.teleport(obj)
            } catch (exception: TeleportationException) {
                onTeleportationFailed(obj, exception)
            }
        }
    }
}
