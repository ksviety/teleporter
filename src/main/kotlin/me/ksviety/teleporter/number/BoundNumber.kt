package me.ksviety.teleporter.number

class BoundNumber(
    private val range: IntRange,
    private val origin: Number
) : Number {

    override fun convertToInt(): Int {
        return origin.convertToInt().coerceIn(range)
    }
}
