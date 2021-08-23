package me.ksviety.teleporter.position.shift

import me.ksviety.teleporter.Position
import me.ksviety.teleporter.position.Vec3iPosition
import net.minecraft.util.math.Vec3i

class HorizontalShift(private val length: Int) : Shift {

    override fun applyTo(position: Position): Position {
        val origin = position.convertToVec3i()

        return Vec3iPosition(
            Vec3i(
                origin.x + length,
                origin.y,
                origin.z
            )
        )
    }
}
