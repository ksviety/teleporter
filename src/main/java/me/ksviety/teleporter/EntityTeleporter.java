package me.ksviety.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class EntityTeleporter {
    private final PositionProvider positionProvider;

    protected EntityTeleporter(PositionProvider positionProvider) {
        this.positionProvider = positionProvider;
    }

    public void teleport(Entity entity) {
        Position position = positionProvider.provide();

        final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        server.commandManager.executeCommand(
                server,
                String.format("/tp %s %d %d %d", entity.getName(), position.getX(), position.getY(), position.getZ())
        );
    }
}
