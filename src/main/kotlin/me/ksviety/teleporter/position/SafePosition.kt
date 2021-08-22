package me.ksviety.teleporter.position

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.ksviety.teleporter.Position
import net.minecraft.world.World
import net.minecraft.util.math.Vec3i
import me.ksviety.teleporter.ShiftAxis
import net.minecraft.util.math.BlockPos
import me.ksviety.teleporter.ShiftedVec3i
import me.ksviety.teleporter.exceptions.CannotFindClosestSafePositionException

/**
 * Can throw <a>CannotFindClosestSafePositionException</a>
 */
class SafePosition(
    private val position: Position,
    private val world: World,
    private val bannedBlocks: Collection<String>,
    private val shiftRadius: Int,
    private val maxSearchIterations: Int
) : Position {
    private val Vec3i.isSafe: Boolean
        get() {
            val positionBelowPlayer = BlockPos(
                ShiftedVec3i.shift(this, 1, ShiftAxis.Down)
            )

            if (world.isAirBlock(positionBelowPlayer))
                return false

            val name = world.getBlockState(positionBelowPlayer).block.registryName?.resourcePath ?: return false

            if (bannedBlocks.contains(name))
                return false

            return true
        }

    override fun getValue(): Vec3i {
        return getSafePosition()
    }

    private fun getSafePosition(iteration: Int): Vec3i {
        val position = position.getValue()

        for (shift in 0 until shiftRadius) {
            for (axis in SHIFT_DIRECTIONS) {
                val topBlockPosition = world.getTopSolidOrLiquidBlock(
                    BlockPos(
                        ShiftedVec3i.shift(position, shift, axis)
                    )
                )

                println("Looking at: ${topBlockPosition as Vec3i}")

                // Shift up by one to avoid spawning inside the block
                val shiftedPosition = ShiftedVec3i.shift(topBlockPosition, 1, ShiftAxis.Up)

                if (shiftedPosition.isSafe)
                    return shiftedPosition
            }
        }

        if (iteration >= maxSearchIterations)
            throw CannotFindClosestSafePositionException

        return getSafePosition(iteration + 1)
    }

    private fun getSafePosition() = getSafePosition(0)

    companion object {
        private val SHIFT_DIRECTIONS = arrayOf(
            ShiftAxis.Forward,
            ShiftAxis.Backward,
            ShiftAxis.Right,
            ShiftAxis.Left
        )
    }
}
