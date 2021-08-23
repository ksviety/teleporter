package me.ksviety.teleporter.position.shift

import me.ksviety.teleporter.Position

interface Shift {

    fun applyTo(position: Position): Position
}
