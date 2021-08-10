package me.ksviety.teleporter.teleporters;

import me.ksviety.teleporter.IEntityTeleporter;
import me.ksviety.teleporter.cache.TeleportationCache;
import net.minecraft.entity.Entity;

import java.util.Collection;

public class OneTimeEntityTeleporter implements IEntityTeleporter {
    private final IEntityTeleporter entityTeleporter;
    private final TeleportationCache cache;

    public OneTimeEntityTeleporter(IEntityTeleporter entityTeleporter, TeleportationCache cache) {
        this.entityTeleporter = entityTeleporter;
        this.cache = cache;
    }

    @Override
    public void teleport(Entity entity) {
        final String name = entity.getName();
        final Collection<String> teleported = cache.getAlreadyTeleportedPlayers();

        if (teleported.contains(name))
            return;

        entityTeleporter.teleport(entity);
        cache.addPlayerAsTeleported(name);
    }
}
