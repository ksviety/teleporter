package me.ksviety.teleporter.data.exceptions

import me.ksviety.teleporter.exceptions.TeleporterException

open class DataException(message: String? = null, cause: Throwable? = null) : TeleporterException(message, cause)
