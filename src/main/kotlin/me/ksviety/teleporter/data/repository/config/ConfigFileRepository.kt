package me.ksviety.teleporter.data.repository.config

import me.ksviety.teleporter.Config
import me.ksviety.teleporter.data.loaders.safe.SafeConfigFileLoader
import me.ksviety.teleporter.data.repository.FileRepository
import me.ksviety.teleporter.data.savers.ConfigFileSaver
import java.io.File

class ConfigFileRepository(file: File) : FileRepository<Config>(ConfigFileSaver(file), SafeConfigFileLoader(file))
