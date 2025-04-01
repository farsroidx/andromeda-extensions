@file:Suppress("unused")

package ir.farsroidx.m31

import android.annotation.SuppressLint
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

// TODO: DateTime ===================================================================== DateTime ===

@SuppressLint("SimpleDateFormat")
private val simpleDateFormat = SimpleDateFormat()

/**
 * Returns the current time in milliseconds since the Unix epoch (January 1, 1970).
 *
 * This property provides the current timestamp in Unix format, which is the number of milliseconds
 * that have passed since midnight of January 1, 1970 UTC (Coordinated Universal Time).
 *
 * Example:
 * ```kotlin
 * val timestamp = unixTimestamp
 * println(timestamp) // Output: 1642574534000 (example timestamp)
 * ```
 *
 * @return The current Unix timestamp in milliseconds.
 */
val unixTimestamp: Long
    get() = System.currentTimeMillis()

/**
 * Extension function to convert a Date object to a Unix timestamp (milliseconds).
 *
 * @return The Unix timestamp corresponding to the Date.
 */
fun Date.toUnixTimestamp(): Long = this.time

/**
 * Extension function to convert a Unix timestamp (milliseconds) to a Date object.
 *
 * @return The Date object corresponding to the Unix timestamp.
 */
fun Long.toDateFromUnix(): Date = Date(this)

/**
 * Extension function to calculate the difference between two Date objects in days.
 *
 * @param other The Date object to compare with.
 * @return The difference between the two dates in days.
 */
fun Date.daysDifference(other: Date): Long {
    val diffInMillis = this.time - other.time
    return diffInMillis / (1000 * 60 * 60 * 24) // Convert milliseconds to days
}

/**
 * Extension function to add a specified number of days to a Date object.
 *
 * @param days The number of days to add to the current date.
 * @return A new Date object with the added days.
 */
fun Date.addDays(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_YEAR, days)
    return calendar.time
}

/**
 * Extension function to subtract a specified number of days from a Date object.
 *
 * @param days The number of days to subtract from the current date.
 * @return A new Date object with the subtracted days.
 */
fun Date.subtractDays(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_YEAR, -days)
    return calendar.time
}

/**
 * Extension function to get the start of the day (midnight) for a Date object.
 *
 * @return A new Date object representing the start of the day (midnight) for the given date.
 */
fun Date.startOfDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}

/**
 * Extension function to get the end of the day (11:59:59 PM) for a Date object.
 *
 * @return A new Date object representing the end of the day for the given date.
 */
fun Date.endOfDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.time
}

/**
 * Converts a given timestamp (in milliseconds) to a formatted date string.
 *
 * @param dateTime The timestamp to convert, default is the current time.
 * @param format The format to use for the output string, default is "yyyy-MM-dd hh:mm:ss".
 *
 * Example:
 * ```kotlin
 * val formattedDate = dateTime()
 * println(formattedDate) // Output: "2025-03-31 04:12:15"
 * ```
 *
 * @return The formatted date string based on the given format.
 */
fun dateTime(
    dateTime: Long = System.currentTimeMillis(),
    format: CharSequence = "yyyy-MM-dd hh:mm:ss"
): CharSequence {
    return DateFormat.format(format, Date(dateTime))
}

/**
 * Converts a date string to a timestamp (milliseconds since the Unix epoch).
 *
 * @param format The format of the input date string, default is "yyyy-MM-dd hh:mm:ss".
 *
 * Example:
 * ```kotlin
 * val timestamp = "2025-03-31 04:12:15".asTimeStamp()
 * println(timestamp) // Output: 1679967135000
 * ```
 *
 * @return The corresponding timestamp in milliseconds, or the current time if the parsing fails.
 */
fun String.asTimeStamp(format: String = "yyyy-MM-dd hh:mm:ss"): Long {
    return try {
        simpleDateFormat.applyPattern(format)
        simpleDateFormat.parse(this)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) {
        e.printStackTrace()
        System.currentTimeMillis()
    }
}