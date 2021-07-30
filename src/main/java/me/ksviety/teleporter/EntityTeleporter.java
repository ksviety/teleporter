package me.ksviety.teleporter;

import net.minecraft.entity.Entity;

public class EntityTeleporter {
    private final PositionProvider positionProvider;

    protected EntityTeleporter(PositionProvider positionProvider) {
        this.positionProvider = positionProvider;
    }

    public void teleport(Entity entity) {
        Position position = positionProvider.provide();

        entity.setPosition(
                position.getX(),
                position.getY(),
                position.getZ()
        );
    }
}
