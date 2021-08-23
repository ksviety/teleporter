package me.ksviety.teleporter.position.boundary

import me.ksviety.teleporter.Position

interface Boundary {

    fun bound(position: Position): Position
}
