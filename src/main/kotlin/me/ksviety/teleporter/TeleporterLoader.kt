package me.ksviety.teleporter

import kotlinx.coroutines.*
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
import me.ksviety.teleporter.loaders.ConfigFileLoader
import me.ksviety.teleporter.loaders.TeleportationCacheFileLoader
import me.ksviety.teleporter.loaders.safe.SafeConfigFileLoader
import me.ksviety.teleporter.loaders.safe.SafeTeleportationCacheFileLoader
import me.ksviety.teleporter.savers.TeleportationCacheFileSaver
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.TextComponentString
import net.minecraft.entity.Entity
import net.minecraft.server.MinecraftServer
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Mod
import java.io.File
import java.net.URI
import java.security.SecureRandom
import kotlin.coroutines.CoroutineContext

@Mod(modid = "teleporter", serverSideOnly = true, acceptableRemoteVersions = "*")
class TeleporterLoader {
    private lateinit var teleportationCache: TeleportationCache
    private lateinit var config: Config

    private val teleporterContext = CoroutineScope(newSingleThreadContext(""))

    private val teleportationCacheFileLocation = "./teleportation.cache"
    private val configFileLocation = "./config/teleportation.config"

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(this)

        teleportationCache = SafeTeleportationCacheFileLoader(
            File(teleportationCacheFileLocation)
        ).load()

        config = SafeConfigFileLoader(
            File(configFileLocation)
        ).load()
    }

    @Mod.EventHandler
    fun unload(event: FMLServerStoppingEvent) {
        TeleportationCacheFileSaver(
            File(teleportationCacheFileLocation)
        ).save(teleportationCache)

        teleporterContext.cancel()
    }

    @SubscribeEvent
    fun onPlayerLoggedIn(event: PlayerLoggedInEvent) {
        val player: Entity = event.player

        teleporterContext.launch {
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
                cancel()
            }
        }
    }
}
