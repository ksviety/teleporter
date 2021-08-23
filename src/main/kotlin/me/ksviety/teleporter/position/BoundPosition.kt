package me.ksviety.teleporter.position

import me.ksviety.teleporter.Position
import me.ksviety.teleporter.position.boundary.Boundary
import net.minecraft.util.math.Vec3i

class BoundPosition(
    private val boundary: Boundary,
    private val origin: Position
) : Position {

    override fun convertToVec3i(): Vec3i {
        return boundary.bound(origin).convertToVec3i()
    }
}
