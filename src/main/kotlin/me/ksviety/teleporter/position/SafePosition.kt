package me.ksviety.teleporter.position

import me.ksviety.teleporter.Config
import me.ksviety.teleporter.Position
import net.minecraft.world.World
import net.minecraft.util.math.Vec3i
import net.minecraft.util.math.BlockPos
import me.ksviety.teleporter.exceptions.CannotFindClosestSafePositionException
import me.ksviety.teleporter.position.shift.AplicatalShift
import me.ksviety.teleporter.position.shift.HorizontalShift
import me.ksviety.teleporter.position.shift.ShiftedPosition
import me.ksviety.teleporter.position.shift.VerticalShift

/**
 * Can throw <a>CannotFindClosestSafePositionException</a>
 */
class SafePosition(
    private val config: Config,
    private val world: World,
    private val position: Position,
) : Position {

    override fun convertToVec3i(): Vec3i {
        return getSafePosition().convertToVec3i()
    }

    private fun getSafePosition(iteration: Int): Position {
        val shiftRadius = config.readShiftRadius()

        for (length in -shiftRadius..shiftRadius) {
            listOf(
                ShiftedPosition(
                    HorizontalShift(length),
                    position
                ),
                ShiftedPosition(
                    AplicatalShift(length),
                    position
                )
            ).forEach {
                if (isSafe(it))
                    return ShiftedPosition(
                        VerticalShift(1),
                        it
                    )
            }
        }

        if (iteration >= config.readSearchIterationsLimit())
            throw CannotFindClosestSafePositionException

        return getSafePosition(iteration + 1)
    }

    private fun getSafePosition() = getSafePosition(0)

    private fun isSafe(position: Position): Boolean {
        val block = BlockPos(position.convertToVec3i())

        if (world.isAirBlock(block))
            return false

        val name = world.getBlockState(block).block.registryName?.resourcePath ?: return false

        if (config.readBannedBlocks().contains(name))
            return false

        return true
    }
}
