package me.ksviety.teleporter

import net.minecraft.util.math.Vec3i

fun interface Position {
    fun getValue(): Vec3i
}
