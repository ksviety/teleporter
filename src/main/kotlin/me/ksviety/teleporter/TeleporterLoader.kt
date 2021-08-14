package me.ksviety.teleporter

import kotlinx.coroutines.*
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent
import me.ksviety.teleporter.teleporters.OneTimeEntityTeleporter
import me.ksviety.teleporter.teleporters.CommandEntityTeleporter
import me.ksviety.teleporter.providers.SafePositionProvider
import me.ksviety.teleporter.providers.BoundRandomPositionProvider
import me.ksviety.teleporter.exceptions.CannotFindClosestSafePositionException
import me.ksviety.teleporter.data.repository.Repository
import me.ksviety.teleporter.data.repository.cache.CacheFileRepository
import me.ksviety.teleporter.data.repository.config.ConfigFileRepository
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.TextComponentString
import net.minecraft.entity.Entity
import net.minecraftforge.fml.common.Mod
import java.io.File
import java.security.SecureRandom

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
                    cache
                ).teleport(player)
            } catch (e: CannotFindClosestSafePositionException) {
                (player as EntityPlayerMP).connection.disconnect(
                    TextComponentString("Could not find any safe position to spawn, log in again.")
                )
                cancel()
            }
        }
    }
}
