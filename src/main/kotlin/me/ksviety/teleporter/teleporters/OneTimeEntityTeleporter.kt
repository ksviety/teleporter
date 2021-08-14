package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.EntityTeleporter
import me.ksviety.teleporter.Cache
import net.minecraft.entity.Entity

class OneTimeEntityTeleporter(
    private val entityTeleporter: EntityTeleporter,
    private val cache: Cache
    ) : EntityTeleporter {

    override fun teleport(entity: Entity) {
        val name = entity.name
        val teleported = cache.alreadyTeleportedPlayers

        if (teleported.contains(name))
            return

        entityTeleporter.teleport(entity)
        cache.addPlayerAsTeleported(name)
    }
}
