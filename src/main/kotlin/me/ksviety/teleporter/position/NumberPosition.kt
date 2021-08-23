package me.ksviety.teleporter.position

import me.ksviety.teleporter.Position
import me.ksviety.teleporter.number.Number
import net.minecraft.util.math.Vec3i

class NumberPosition(
    private val x: Number,
    private val y: Number,
    private val z: Number
) : Position {

    override fun convertToVec3i(): Vec3i {
        return Vec3i(
            x.convertToInt(),
            y.convertToInt(),
            z.convertToInt()
        )
    }
}
