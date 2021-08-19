package me.ksviety.teleporter.teleporters.failHandlers

import me.ksviety.teleporter.exceptions.TeleportationException
import me.ksviety.teleporter.teleporters.TeleportationFailHandler
import me.ksviety.teleporter.utilities.PlayerDisconnector
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.text.TextComponentString

class DisconnectingTeleportationFailHandler : TeleportationFailHandler<EntityPlayer> {

    override fun handle(target: EntityPlayer, cause: TeleportationException) {
        PlayerDisconnector(target).disconnect(
            TextComponentString(
                "Could not find any good point to teleport to"
            )
        )
    }
}
