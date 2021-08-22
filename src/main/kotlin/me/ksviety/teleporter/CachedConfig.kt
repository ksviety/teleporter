package me.ksviety.teleporter

class CachedConfig(private val origin: Config) : Config {
    private val bannedBlocks by lazy { origin.readBannedBlocks() }
    private val centerX by lazy { origin.readCenterX() }
    private val centerZ by lazy { origin.readCenterZ() }
    private val size by lazy { origin.readSize() }
    private val shiftRadius by lazy { origin.readShiftRadius() }
    private val searchIterationsLimit by lazy { origin.readSearchIterationsLimit() }
    private val spawnPosition by lazy { origin.readSpawnPosition() }

    override fun readBannedBlocks() = bannedBlocks

    override fun readCenterX() = centerX

    override fun readCenterZ() = centerZ

    override fun readSize() = size

    override fun readShiftRadius() = shiftRadius

    override fun readSearchIterationsLimit() = searchIterationsLimit

    override fun readSpawnPosition() = spawnPosition
}
