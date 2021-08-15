package me.ksviety.teleporter.exceptions

import java.lang.RuntimeException

object CannotFindClosestSafePositionException :
    RuntimeException("Could not find any safe block near the provided position")
