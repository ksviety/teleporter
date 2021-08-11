package me.ksviety.teleporter.providers

import me.ksviety.teleporter.PositionProvider
import net.minecraft.util.math.Vec3i
import java.util.*

class BoundRandomPositionProvider(
    private val centerX: Int,
    private val centerZ: Int,
    private val size: Int,
    private val random: Random
) : PositionProvider {
    private val randomCoordinate: Double
        get() = random.nextInt(size).toDouble() / size.toDouble()

    override fun provide(): Vec3i {
        val x = randomCoordinate
        val z = randomCoordinate
        val y = randomCoordinate

        val minX = centerX - size
        val minZ = centerZ - size
        val minY = 0

        val maxX = centerX + size
        val maxZ = centerZ + size
        val maxY = 255

        return Vec3i(
            x.scale(minX.toDouble(), maxX.toDouble()),
            y.scale(minY.toDouble(), maxY.toDouble()),
            z.scale(minZ.toDouble(), maxZ.toDouble())
        )
    }

    private fun Double.scale(min: Double, max: Double): Int {
        return (this * (max - min) + min).toInt()
    }
}
