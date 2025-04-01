@file:Suppress("KotlinConstantConditions", "unused")

package ir.farsroidx.m31

// TODO: Number ========================================================================= Number ===

/**
 * Calculates the percentage of a given value.
 *
 * This method computes the percentage of a number relative to another value. For example,
 * if `this` is 20 and `value` is 200, it will return 40.0 (20% of 200).
 *
 * Example usage:
 * ```kotlin
 * val percentage = 20 percentOf 200  // result is 40.0
 * val fullValue = 100 percentOf 250  // result is 250.0
 * ```
 *
 * @param value The value on which the percentage is to be calculated.
 * @return The calculated percentage of the value.
 */
infix fun Int.percentOf(value: Number): Float {
    return when {
        this == 0   -> 0F                              // 0% of any value is 0
        this == 100 -> value.toFloat()                 // 100% of a value is the value itself
        else        -> (value.toFloat() * this / 100F) // Calculate percentage
    }
}