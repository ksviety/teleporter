package me.ksviety.teleporter.exceptions

import net.minecraft.entity.player.EntityPlayer

sealed class TeleportationException(
    val player: EntityPlayer,
    message: String? = null,
    cause: Throwable? = null
) : TeleporterException(message, cause)
