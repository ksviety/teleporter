package me.ksviety.teleporter.providers

import me.ksviety.teleporter.PositionProvider

/**
 * Saves the position it provides first time,
 * so the following calls will simply return
 * the cached value instead of recalculating
 */
class CachedPositionProvider(private val original: PositionProvider) : PositionProvider {
    private val cache by lazy { original.provide() }

    override fun provide() = cache
}
