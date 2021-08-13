package me.ksviety.teleporter

import me.ksviety.teleporter.cache.TeleportationCache
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent
import me.ksviety.teleporter.teleporters.OneTimeEntityTeleporter
import me.ksviety.teleporter.teleporters.CommandEntityTeleporter
import me.ksviety.teleporter.providers.SafePositionProvider
import me.ksviety.teleporter.providers.BoundRandomPositionProvider
import me.ksviety.teleporter.exceptions.CannotFindClosetSafePositionException
import me.ksviety.teleporter.loaders.TeleportationCacheFileLoader
import me.ksviety.teleporter.savers.TeleportationCacheFileSaver
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.TextComponentString
import java.io.IOException
import net.minecraft.entity.Entity
import net.minecraftforge.fml.common.Mod
import java.io.File
import java.io.FileNotFoundException
import java.net.URI
import java.security.SecureRandom

@Mod(modid = Teleporter.MOD_ID, serverSideOnly = true, acceptableRemoteVersions = "*")
class Teleporter {
    private lateinit var teleportationCache: TeleportationCache
    private lateinit var config: Config

    private val teleportationCacheFileLocation = "./teleportation.cache"

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(this)

        teleportationCache = TeleportationCacheFileLoader(URI(teleportationCacheFileLocation)).load()
        config = loadConfig()
    }

    @Mod.EventHandler
    fun unload(event: FMLServerStoppingEvent) {
        TeleportationCacheFileSaver(URI(teleportationCacheFileLocation)).save(teleportationCache)
    }

    @SubscribeEvent
    fun onPlayerLoggedIn(event: PlayerLoggedInEvent) {
        val player: Entity = event.player

        try {
            val world = player.entityWorld
            OneTimeEntityTeleporter(
                CommandEntityTeleporter(
                    SafePositionProvider(
                        BoundRandomPositionProvider(
                            config.centerX,
                            config.centerZ,
                            config.size,
                            SecureRandom()
                        ),
                        world,
                        config.getBannedBlocks(),
                        config.shiftRadius,
                        config.searchIterationsLimit
                    )
                ),
                teleportationCache
            ).teleport(player)
        } catch (e: CannotFindClosetSafePositionException) {
            (player as EntityPlayerMP).connection.disconnect(
                TextComponentString("Could not find any safe position to spawn, log in again.")
            )
        }
    }

    private fun loadConfig(): Config {
        val configFile = File("./config/teleporter.config")

        if (!configFile.exists()) {
            try {
                configFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                throw InternalError("Could not create cache file!")
            }
        }

        return try {
            Config.fromFile(configFile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            throw InternalError("Could not create cache file!")
        }
    }

    companion object {
        const val MOD_ID = "teleporter"
    }
}
