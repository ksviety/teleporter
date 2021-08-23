package me.ksviety.teleporter.position

import me.ksviety.teleporter.Config
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
    private val config: Config,
    private val world: World,
    private val position: Position,
) : Position {
    private val Vec3i.isSafe: Boolean
        get() {
            val positionBelowPlayer = BlockPos(
                ShiftedVec3i.shift(this, 1, ShiftAxis.Down)
            )

            if (world.isAirBlock(positionBelowPlayer))
                return false

            val name = world.getBlockState(positionBelowPlayer).block.registryName?.resourcePath ?: return false

            if (config.readBannedBlocks().contains(name))
                return false

            return true
        }

    override fun convertToVec3i(): Vec3i {
        return getSafePosition()
    }

    private fun getSafePosition(iteration: Int): Vec3i {
        val position = position.convertToVec3i()

        for (shift in 0 until config.readShiftRadius()) {
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

        if (iteration >= config.readSearchIterationsLimit())
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
