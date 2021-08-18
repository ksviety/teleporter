package me.ksviety.teleporter

import net.minecraft.util.math.Vec3i

class Config(
    private val bannedBlocks: Set<String>,
    val centerX: Int,
    val centerZ: Int,
    val size: Int,
    val shiftRadius: Int,
    val searchIterationsLimit: Int,
    val spawn: Vec3i
) {
    fun getBannedBlocks(): Collection<String> {
        return bannedBlocks
    }

    companion object {
        val default = Config(
            setOf("lava", "water"),
            0, 0, 1000,
            200, 10_000,
            Vec3i(0, 100, 0)
        )
    }
}
