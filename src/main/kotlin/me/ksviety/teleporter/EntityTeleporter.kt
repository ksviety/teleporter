package me.ksviety.teleporter

import net.minecraft.entity.Entity

interface EntityTeleporter {
    fun teleport(entity: Entity)
}
