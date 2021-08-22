package me.ksviety.teleporter

import net.minecraft.util.math.Vec3i

interface Cache {

    fun readPlayers(): Map<String, Vec3i>

    fun recordPlayer(name: String, spawn: Vec3i)

    fun contains(name: String): Boolean

    fun save()
}
