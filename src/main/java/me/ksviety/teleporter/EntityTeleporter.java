package me.ksviety.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class EntityTeleporter {
    private final IPositionProvider positionProvider;

    protected EntityTeleporter(IPositionProvider positionProvider) {
        this.positionProvider = positionProvider;
    }

    public void teleport(Entity entity) {
        Vec3i position = positionProvider.provide();

        final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        server.commandManager.executeCommand(
                server,
                String.format("/tp %s %d %d %d", entity.getName(), position.getX(), position.getY(), position.getZ())
        );
    }
}
