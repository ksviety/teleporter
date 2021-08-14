package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.PositionProvider
import me.ksviety.teleporter.Teleporter
import net.minecraft.entity.Entity

class EntityTeleporter(private val positionProvider: PositionProvider) : Teleporter<Entity> {

    override fun teleport(obj: Entity) {
        val position = positionProvider.provide()

        val x = position.x.toDouble()
        val y = position.y.toDouble()
        val z = position.z.toDouble()

        obj.setPositionAndUpdate(x, y, z)
    }
}
