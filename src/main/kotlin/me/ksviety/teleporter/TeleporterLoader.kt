package me.ksviety.teleporter

import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.*
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.*;
import me.ksviety.teleporter.position.SafePosition
import me.ksviety.teleporter.position.BoundRandomPosition
import me.ksviety.teleporter.exceptions.CannotFindClosestSafePositionException
import me.ksviety.teleporter.teleporters.EntityTeleporter
import me.ksviety.teleporter.teleporters.OneTimePlayerTeleporter
import me.ksviety.teleporter.teleporters.PointSavingPlayerTeleporter
import me.ksviety.teleporter.teleporters.StunningPlayerTeleporter
import me.ksviety.teleporter.utilities.PlayerDisconnector
import net.minecraft.entity.Entity
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.fml.common.Mod
import java.io.File
import java.io.FileReader
import java.security.SecureRandom
import java.util.*

@Mod(modid = "teleporter", serverSideOnly = true, acceptableRemoteVersions = "*")
class TeleporterLoader {
    private lateinit var cache: Cache
    private lateinit var config: Config

    private val teleporterContext = CoroutineScope(newSingleThreadContext(""))

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(this)

        cache = FileCache(
            File("teleportation.cache")
        )

        config = CachedConfig(
            JsonConfig(
                JsonParser().parse(
                    JsonReader(
                        FileReader(
                            File("./config/teleporter.config")
                        )
                    )
                ).asJsonObject
            )
        )
    }

    @Mod.EventHandler
    fun unload(event: FMLServerStoppingEvent) {
        cache.save()

        teleporterContext.cancel()
    }

    @SubscribeEvent
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val player = event.player

        if (Objects.isNull(player.getBedLocation())) {
            EntityTeleporter { cache.readPlayers()[player.name]!! }.teleport(player)
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
                        spawn = config.readSpawnPosition(),
                        original = PointSavingPlayerTeleporter(
                            SafePosition(
                                config,
                                world,
                                BoundRandomPosition(
                                    config,
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
