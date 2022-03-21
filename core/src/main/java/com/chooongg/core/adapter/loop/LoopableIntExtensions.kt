package com.chooongg.core.adapter.loop


/**
 * Returns the value of the Int plus the given [amount], but "loops" the value to keep it between
 * the [count] and zero.
 */
internal fun Int.loop(amount: Int, count: Int): Int {
    var newVal = this + amount
    newVal %= count
    if (newVal < 0) newVal += count
    return newVal
}

/**
 * Returns the value of the Int plus one, but "loops" the value to keep it between the [count] and zero.
 */
internal fun Int.loopedIncrement(count: Int): Int {
    return this.loop(1, count)
}

/**
 * Returns the value of the Int minus one, but "loops" the value to keep it between the [count] and zero.
 */
internal fun Int.loopedDecrement(count: Int): Int {
    return this.loop(-1, count)
}