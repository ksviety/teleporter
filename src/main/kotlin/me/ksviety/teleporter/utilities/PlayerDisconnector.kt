package me.ksviety.teleporter.utilities

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.ITextComponent

class PlayerDisconnector(player: EntityPlayer) {
    private val connection = (player as EntityPlayerMP).connection

    fun disconnect(reason: ITextComponent) = connection.disconnect(reason)
}
