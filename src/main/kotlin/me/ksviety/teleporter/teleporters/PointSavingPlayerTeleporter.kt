package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.PositionProvider
import me.ksviety.teleporter.Teleporter
import me.ksviety.teleporter.providers.CachedPositionProvider
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos

class PointSavingPlayerTeleporter(provider: PositionProvider) : Teleporter<EntityPlayer> {
    private val cachedProvider = CachedPositionProvider(provider)
    private val teleporter = EntityTeleporter(cachedProvider)

    override fun teleport(obj: EntityPlayer) {
        val position = cachedProvider.provide()

        obj.setSpawnPoint(BlockPos(position), false)

        teleporter.teleport(obj)
    }
}
