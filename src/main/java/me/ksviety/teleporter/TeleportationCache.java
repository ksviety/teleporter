package me.ksviety.teleporter;

import java.util.Collection;
import java.util.Set;

public abstract class TeleportationCache {
    private final Set<String> alreadyTeleportedPlayers;

    protected TeleportationCache(Set<String> alreadyTeleportedPlayers) {
        this.alreadyTeleportedPlayers = alreadyTeleportedPlayers;
    }

    public Collection<String> getAlreadyTeleportedPlayers() {
        return alreadyTeleportedPlayers;
    }

    public void addPlayerAsTeleported(String name) {
        alreadyTeleportedPlayers.add(name);
        addPlayerToCache(name);
    }

    public abstract void save();

    protected abstract void addPlayerToCache(String name);
}
