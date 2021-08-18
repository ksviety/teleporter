package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.Teleporter
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer

class StunningPlayerTeleporter(private val original: Teleporter<EntityPlayer>) : Teleporter<EntityPlayer> {

    override fun teleport(obj: EntityPlayer) {
        val originalSpeed = obj.capabilities.walkSpeed

        obj.capabilities.setPlayerWalkSpeed(0F)

        original.teleport(obj)

        obj.capabilities.setPlayerWalkSpeed(originalSpeed)
    }
}
