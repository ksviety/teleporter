package me.ksviety.teleporter.position.boundary

import me.ksviety.teleporter.Position
import me.ksviety.teleporter.number.BoundNumber
import me.ksviety.teleporter.number.IntNumber
import me.ksviety.teleporter.position.NumberPosition

class ThreeDimensionalBoundary(
    private val horizontalBoundary: IntRange,
    private val verticalBoundary: IntRange,
    private val aplicatalBoundary: IntRange
) : Boundary {

    override fun bound(position: Position): Position {
        val origin = position.convertToVec3i()

        return NumberPosition(
            BoundNumber(
                horizontalBoundary,
                IntNumber(origin.x)
            ),
            BoundNumber(
                verticalBoundary,
                IntNumber(origin.y)
            ),
            BoundNumber(
                aplicatalBoundary,
                IntNumber(origin.z)
            )
        )
    }
}
