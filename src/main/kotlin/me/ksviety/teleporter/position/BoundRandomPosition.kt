package me.ksviety.teleporter.position

import me.ksviety.teleporter.Position
import net.minecraft.util.math.Vec3i
import java.util.*

class BoundRandomPosition(
    private val centerX: Int,
    private val centerZ: Int,
    private val size: Int,
    private val random: Random
) : Position {
    private val randomCoordinate: Double
        get() = random.nextInt(size).toDouble() / size.toDouble()

    override fun getValue(): Vec3i {
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
