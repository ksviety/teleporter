package me.ksviety.teleporter.utilities

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.ITextComponent

class PlayerConnection(player: EntityPlayer) {
    private val connection = (player as EntityPlayerMP).connection

    fun close(reason: ITextComponent) = connection.disconnect(reason)
}
