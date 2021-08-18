package me.ksviety.teleporter.teleporters

import kotlinx.coroutines.*
import me.ksviety.teleporter.Teleporter
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.Vec3i

class StunningPlayerTeleporter(private val original: Teleporter<EntityPlayer>, private val spawn: Vec3i) : Teleporter<EntityPlayer> {

    override fun teleport(obj: EntityPlayer) {
        val teleportationJob = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                delay(1)
                EntityTeleporter { spawn }.teleport(obj)
            }
        }

        original.teleport(obj)
        teleportationJob.cancel()
    }
}
