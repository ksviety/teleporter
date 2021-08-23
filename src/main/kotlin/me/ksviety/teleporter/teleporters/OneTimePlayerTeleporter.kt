package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.Cache
import me.ksviety.teleporter.Teleporter
import net.minecraft.entity.player.EntityPlayer

open class OneTimePlayerTeleporter(
    private val cache: Cache,
    private val original: Teleporter<EntityPlayer>,
    ) : Teleporter<EntityPlayer> {

    override fun teleport(obj: EntityPlayer) {
        if (cache.contains(obj.name))
            return

        original.teleport(obj)
        cache.recordPlayer(obj.name, obj.position)
    }
}
