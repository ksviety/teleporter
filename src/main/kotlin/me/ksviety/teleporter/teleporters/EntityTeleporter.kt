package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.Position
import me.ksviety.teleporter.Teleporter
import net.minecraft.entity.Entity

class EntityTeleporter(private val position: Position) : Teleporter<Entity> {

    override fun teleport(obj: Entity) {
        val position = position.getValue()

        val x = position.x.toDouble()
        val y = position.y.toDouble()
        val z = position.z.toDouble()

        obj.setPositionAndUpdate(x, y, z)
    }
}
