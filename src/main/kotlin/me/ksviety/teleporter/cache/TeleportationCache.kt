package me.ksviety.teleporter.cache

class TeleportationCache(alreadyTeleportedPlayers: Set<String>) {
    private val _alreadyTeleportedPlayers = HashSet(alreadyTeleportedPlayers)

    val alreadyTeleportedPlayers: Collection<String>
        get() {
            return _alreadyTeleportedPlayers
        }

    fun addPlayerAsTeleported(name: String) {
        _alreadyTeleportedPlayers.add(name)
    }
}
