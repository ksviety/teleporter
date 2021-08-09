package me.ksviety.teleporter;

import me.ksviety.teleporter.cache.FileTeleportationCache;
import me.ksviety.teleporter.cache.TeleportationCache;
import me.ksviety.teleporter.providers.BoundRandomPositionProvider;
import me.ksviety.teleporter.providers.SafePositionProvider;
import me.ksviety.teleporter.teleporters.EntityTeleporter;
import me.ksviety.teleporter.teleporters.OneTimeEntityTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;

@Mod(modid = Teleporter.MOD_ID, serverSideOnly = true, acceptableRemoteVersions = "*")
public class Teleporter
{
    public static final String MOD_ID = "teleporter";

    private TeleportationCache teleportationCache;
    private Config config;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        teleportationCache = loadTeleportationCache();
        config = loadConfig();
    }


    @EventHandler
    public void unload(FMLServerStoppingEvent event) {
        teleportationCache.save();
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        final Entity player = event.player;
        final World world = player.getEntityWorld();

        new OneTimeEntityTeleporter(
                new EntityTeleporter(
                        new SafePositionProvider(
                                new BoundRandomPositionProvider(
                                        config.getCenterX(),
                                        config.getCenterZ(),
                                        config.getSize(),
                                        new SecureRandom()
                                ),
                                world,
                                config.getBannedBlocks(),
                                config.getShiftRadius()
                        )
                ),
                teleportationCache
        ).teleport(player);
    }

    private TeleportationCache loadTeleportationCache() {
        final File cache = new File("teleportation.cache");

        TeleportationCache teleportationCache;

        if (!cache.exists()) {
            try {
                cache.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new InternalError("Could not create cache file!");
            }
        }

        try {
            teleportationCache = FileTeleportationCache.fromFile(cache);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new InternalError("Could not create cache file!");
        }

        return teleportationCache;
    }

    private Config loadConfig() {
        final File configFile = new File("./config/teleporter.config");

        Config config;

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new InternalError("Could not create cache file!");
            }
        }

        try {
            config = Config.fromFile(configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new InternalError("Could not create cache file!");
        }

        return config;
    }
}
