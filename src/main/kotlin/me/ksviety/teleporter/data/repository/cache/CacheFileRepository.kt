package me.ksviety.teleporter.data.repository.cache

import me.ksviety.teleporter.Cache
import me.ksviety.teleporter.data.loaders.safe.SafeCacheFileLoader
import me.ksviety.teleporter.data.repository.FileRepository
import me.ksviety.teleporter.data.savers.CacheFileSaver
import java.io.File

class CacheFileRepository(file: File) : FileRepository<Cache>(CacheFileSaver(file), SafeCacheFileLoader(file))
