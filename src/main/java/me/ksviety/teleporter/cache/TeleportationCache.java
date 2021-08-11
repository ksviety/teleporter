package me.ksviety.teleporter.cache;

import me.ksviety.teleporter.Cache;

import java.util.Collection;
import java.util.Set;

public abstract class TeleportationCache implements Cache {
    private final Set<String> alreadyTeleportedPlayers;

    protected TeleportationCache(Set<String> alreadyTeleportedPlayers) {
        this.alreadyTeleportedPlayers = alreadyTeleportedPlayers;
    }

    public Collection<String> getAlreadyTeleportedPlayers() {
        return alreadyTeleportedPlayers;
    }

    public void addPlayerAsTeleported(String name) {
        alreadyTeleportedPlayers.add(name);
    }
}
