package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.Position
import me.ksviety.teleporter.Teleporter
import me.ksviety.teleporter.position.CachedPosition
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos

class PointSavingPlayerTeleporter(provider: Position) : Teleporter<EntityPlayer> {
    private val cachedProvider = CachedPosition(provider)
    private val teleporter = EntityTeleporter(cachedProvider)

    override fun teleport(obj: EntityPlayer) {
        val position = cachedProvider.convertToVec3i()

        obj.setSpawnPoint(BlockPos(position), false)

        teleporter.teleport(obj)
    }
}
