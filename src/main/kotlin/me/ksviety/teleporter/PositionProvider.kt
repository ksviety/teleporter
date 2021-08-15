package me.ksviety.teleporter

import net.minecraft.util.math.Vec3i

fun interface PositionProvider {
    fun provide(): Vec3i
}
