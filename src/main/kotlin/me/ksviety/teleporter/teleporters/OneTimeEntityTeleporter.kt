package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.EntityTeleporter
import me.ksviety.teleporter.cache.TeleportationCache
import net.minecraft.entity.Entity

class OneTimeEntityTeleporter(
    private val entityTeleporter: EntityTeleporter,
    private val cache: TeleportationCache
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
