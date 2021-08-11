package me.ksviety.teleporter.cache

import java.io.*
import java.util.HashSet

class FileTeleportationCache private constructor(file: File, players: Set<String>) : TeleportationCache(HashSet(players)) {
    private var fileOutput = runCatching { BufferedWriter(FileWriter(file, false)) }
        .getOrNull() ?: throw InternalError("Cannot write to cache file!")

    override fun save() {
        try {
            for (player in alreadyTeleportedPlayers) {
                fileOutput.append(player)
                fileOutput.newLine()
            }

            fileOutput.flush()
            fileOutput.close()
        } catch (e: IOException) {
            e.printStackTrace()
            throw InternalError("Cannot write to cache file!")
        }
    }

    companion object {

        fun fromFile(file: File): FileTeleportationCache {
            val players: MutableSet<String> = HashSet()
            val reader = BufferedReader(FileReader(file))

            try {
                var name: String?

                while (true) {
                    name = reader.readLine()

                    if (name == null)
                        break

                    players.add(name)
                }

                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
                throw InternalError("Cannot read from cache file!")
            }

            return FileTeleportationCache(file, players)
        }
    }
}
