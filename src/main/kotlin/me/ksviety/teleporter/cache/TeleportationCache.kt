package me.ksviety.teleporter.cache

import me.ksviety.teleporter.Cache

abstract class TeleportationCache protected constructor(private val _alreadyTeleportedPlayers: MutableSet<String>) :
    Cache {

    val alreadyTeleportedPlayers: Collection<String>
        get() {
            return _alreadyTeleportedPlayers
        }

    fun addPlayerAsTeleported(name: String) {
        _alreadyTeleportedPlayers.add(name)
    }
}
