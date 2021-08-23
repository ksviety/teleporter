package me.ksviety.teleporter.position.shift

import me.ksviety.teleporter.Position
import net.minecraft.util.math.Vec3i

class ShiftedPosition(private val shift: Shift, private val origin: Position) : Position {

    override fun convertToVec3i(): Vec3i {
        val shiftedOrigin = shift.applyTo(origin)
        return shiftedOrigin.convertToVec3i()
    }
}
