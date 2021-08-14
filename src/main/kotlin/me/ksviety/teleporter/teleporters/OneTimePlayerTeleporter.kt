package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.Cache
import me.ksviety.teleporter.Teleporter
import net.minecraft.entity.player.EntityPlayer

open class OneTimePlayerTeleporter(
    private val entityTeleporter: Teleporter<EntityPlayer>,
    private val cache: Cache
    ) : Teleporter<EntityPlayer> {

    override fun teleport(obj: EntityPlayer) {
        val name = obj.name
        val teleported = cache.alreadyTeleportedPlayers

        if (teleported.contains(name))
            return

        entityTeleporter.teleport(obj)
        cache.addPlayerAsTeleported(name)
    }
}
