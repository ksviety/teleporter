package me.ksviety.teleporter

import net.minecraft.util.math.Vec3i

class ShiftedVec3i private constructor(x: Int, y: Int, z: Int) : Vec3i(x, y, z) {
    companion object {

        fun shift(original: Vec3i, shift: Int, axis: ShiftAxis): ShiftedVec3i {
            fun Int.bidirectionalizedByAxis(positive: ShiftAxis, negative: ShiftAxis): Int = when (axis) {
                positive -> +this
                negative -> -this
                else -> 0
            }

            val x = original.x + shift.bidirectionalizedByAxis(ShiftAxis.Right, ShiftAxis.Left)
            val y = original.y + shift.bidirectionalizedByAxis(ShiftAxis.Up, ShiftAxis.Down)
            val z = original.z + shift.bidirectionalizedByAxis(ShiftAxis.Forward, ShiftAxis.Backward)

            return ShiftedVec3i(x, y, z)
        }
    }
}
