package me.ksviety.teleporter.cache;

import me.ksviety.teleporter.ICache;

import java.util.Collection;
import java.util.Set;

public abstract class TeleportationCache implements ICache {
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
