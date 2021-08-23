package me.ksviety.teleporter.position.shift

import me.ksviety.teleporter.Position
import me.ksviety.teleporter.position.Vec3iPosition
import net.minecraft.util.math.Vec3i

class AplicatalShift(private val length: Int) : Shift {

    override fun applyTo(position: Position): Position {
        val origin = position.convertToVec3i()

        return Vec3iPosition(
            Vec3i(
                origin.x,
                origin.y,
                origin.z + length
            )
        )
    }
}
