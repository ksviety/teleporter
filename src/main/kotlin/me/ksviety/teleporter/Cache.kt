package me.ksviety.teleporter

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.Vec3i
import java.util.*

class Cache(alreadyTeleportedPlayers: Map<String, Vec3i>) {
    private val _alreadyTeleportedPlayers = HashMap(alreadyTeleportedPlayers)

    val alreadyTeleportedPlayers: Map<String, Vec3i>
        get() = _alreadyTeleportedPlayers

    operator fun set(player: EntityPlayer, position: Vec3i) {
        _alreadyTeleportedPlayers[player.asKey] = position
    }

    operator fun get(player: EntityPlayer) = _alreadyTeleportedPlayers[player.asKey]

    operator fun contains(player: EntityPlayer) = _alreadyTeleportedPlayers.containsKey(player.asKey)

    private val EntityPlayer.asKey: String
        get() = this.name
}
