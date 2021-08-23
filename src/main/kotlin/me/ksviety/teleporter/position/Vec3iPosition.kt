package me.ksviety.teleporter.position

import me.ksviety.teleporter.Position
import net.minecraft.util.math.Vec3i

class Vec3iPosition(private val vec3i: Vec3i) : Position {

    override fun convertToVec3i() = vec3i
}
