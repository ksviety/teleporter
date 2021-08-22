package me.ksviety.teleporter.position

import me.ksviety.teleporter.Position

/**
 * Saves the position it provides first time,
 * so the following calls will simply return
 * the cached value instead of recalculating
 */
class CachedPosition(private val original: Position) : Position {
    private val cache by lazy { original.getValue() }

    override fun getValue() = cache
}
