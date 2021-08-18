package me.ksviety.teleporter

import kotlinx.coroutines.*
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.*;
import me.ksviety.teleporter.providers.SafePositionProvider
import me.ksviety.teleporter.providers.BoundRandomPositionProvider
import me.ksviety.teleporter.exceptions.CannotFindClosestSafePositionException
import me.ksviety.teleporter.data.repository.Repository
import me.ksviety.teleporter.data.repository.cache.CacheFileRepository
import me.ksviety.teleporter.data.repository.config.ConfigFileRepository
import me.ksviety.teleporter.teleporters.EntityTeleporter
import me.ksviety.teleporter.teleporters.OneTimePlayerTeleporter
import me.ksviety.teleporter.teleporters.PointSavingPlayerTeleporter
import me.ksviety.teleporter.teleporters.StunningPlayerTeleporter
import me.ksviety.teleporter.utilities.PlayerDisconnector
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.fml.common.Mod
import java.io.File
import java.security.SecureRandom
import java.util.*

@Mod(modid = "teleporter", serverSideOnly = true, acceptableRemoteVersions = "*")
class TeleporterLoader {
    private lateinit var cache: Cache
    private lateinit var config: Config

    private val teleporterContext = CoroutineScope(newSingleThreadContext(""))

    private val cacheRepository: Repository<Cache> = CacheFileRepository(File("./teleportation.cache"))
    private val configRepository: Repository<Config> = ConfigFileRepository(File("./config/teleportation.config"))

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(this)

        cache = cacheRepository.load()

        config = configRepository.load()
    }

    @Mod.EventHandler
    fun unload(event: FMLServerStoppingEvent) {
        cacheRepository.save(cache)

        teleporterContext.cancel()
    }

    @SubscribeEvent
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val player = event.player

        if (Objects.isNull(player.getBedLocation())) {
            EntityTeleporter { cache[player]!! }.teleport(player)
        }
    }

    @SubscribeEvent
    fun onPlayerLoggedIn(event: PlayerLoggedInEvent) {
        val player = event.player

        teleporterContext.launch {
            try {
                val world = (player as Entity).entityWorld
                OneTimePlayerTeleporter(
                    cache = cache,
                    original = StunningPlayerTeleporter(
                        PointSavingPlayerTeleporter(
                            SafePositionProvider(
                                world = world,
                                bannedBlocks = config.getBannedBlocks(),
                                shiftRadius = config.shiftRadius,
                                maxSearchIterations = config.searchIterationsLimit,
                                positionProvider = BoundRandomPositionProvider(
                                    config.centerX,
                                    config.centerZ,
                                    config.size,
                                    SecureRandom()
                                )
                            )
                        )
                    )
                ).teleport(player)
            } catch (e: CannotFindClosestSafePositionException) {
                PlayerDisconnector(player).disconnect(TextComponentString("Could not find any safe position to spawn, log in again."))
            } finally {
                cancel()
            }
        }
    }
}
