package me.ksviety.teleporter

import net.minecraft.util.math.Vec3i

interface Config {

    fun readBannedBlocks(): Collection<String>

    fun readCenterX(): Int

    fun readCenterZ(): Int

    fun readSize(): Int

    fun readShiftRadius(): Int

    fun readSearchIterationsLimit(): Int

    fun readSpawnPosition(): Vec3i
}
