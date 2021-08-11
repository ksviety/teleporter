package me.ksviety.teleporter

import net.minecraft.util.math.Vec3i

interface PositionProvider {
    fun provide(): Vec3i
}
