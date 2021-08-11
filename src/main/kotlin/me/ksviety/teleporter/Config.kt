package me.ksviety.teleporter

class Config(
    private val bannedBlocks: Set<String>,
    val centerX: Int,
    val centerZ: Int,
    val size: Int,
    val shiftRadius: Int,
    val searchIterationsLimit: Int
) {
    fun getBannedBlocks(): Collection<String> {
        return bannedBlocks
    }

    companion object {
        val default = Config(
            setOf("lava", "water"),
            0, 0, 1000,
            200, 10_000
        )
    }
}
