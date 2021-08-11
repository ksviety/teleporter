package me.ksviety.teleporter.teleporters

import me.ksviety.teleporter.PositionProvider
import me.ksviety.teleporter.EntityTeleporter
import net.minecraft.entity.Entity
import net.minecraftforge.fml.common.FMLCommonHandler

class CommandEntityTeleporter(private val positionProvider: PositionProvider) : EntityTeleporter {

    override fun teleport(entity: Entity) {
        with(FMLCommonHandler.instance().minecraftServerInstance) {
            val position = positionProvider.provide()

            val x = position.x
            val y = position.y
            val z = position.z

            val name = entity.name

            commandManager.executeCommand(this, "/tp $name $x $y $z")
        }
    }
}
