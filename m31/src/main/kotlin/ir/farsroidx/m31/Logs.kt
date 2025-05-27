@file:Suppress("unused")

package ir.farsroidx.m31

import android.os.SystemClock
import android.util.Log

// TODO: Logs ============================================================================= Logs ===

/**
 * Extension function for logging any object using the debug log level.
 *
 * Logs the current object using the `dLog` method.
 *
 * @see dLog
 */
fun Any?.log() = dLog(this)

/**
 * Logs the current object and returns it.
 * This is useful for logging an object while maintaining method chaining.
 *
 * Example:
 * ```
 * val testString = "This is a test String".logging()
 * Log.w("TEST_TAG", testString)
 * ```
 *
 * Logcat:
 * ```
 * Andromeda | D | This is a test String
 * TEST_TAG  | W | This is a test String
 * ```
 *
 * @return The original object after logging.
 */
fun <T: Any> T.logging(): T = this.also { dLog(it) }

/**
 * Logs the provided logs at the verbose log level.
 *
 * @param logs The logs to be printed.
 */
fun vLog(vararg logs: Any?) {
    logs.forEach { log -> Log.v(TAG, log.toString()) }
}

/**
 * Logs the provided logs at the info log level.
 *
 * @param logs The logs to be printed.
 */
fun iLog(vararg logs: Any?) {
    logs.forEach { log -> Log.i(TAG, log.toString()) }
}

/**
 * Logs the provided logs at the debug log level.
 *
 * @param logs The logs to be printed.
 */
fun dLog(vararg logs: Any?) {
    logs.forEach { log -> Log.d(TAG, log.toString()) }
}

/**
 * Logs the provided logs at the warning log level.
 *
 * @param logs The logs to be printed.
 */
fun wLog(vararg logs: Any?) {
    logs.forEach { log -> Log.w(TAG, log.toString()) }
}

/**
 * Logs the provided logs at the error log level.
 *
 * @param logs The logs to be printed.
 */
fun eLog(vararg logs: Any?) {
    logs.forEach { log -> Log.e(TAG, log.toString()) }
}

/**
 * Logs the current memory usage of the application.
 */
fun logMemoryUsage() {

    val runtime = Runtime.getRuntime()

    val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)

    val maxMemory = runtime.maxMemory() / (1024 * 1024)

    val freeMemory = runtime.freeMemory() / (1024 * 1024)

    dLog("Used: ${usedMemory}MB, Free: ${freeMemory}MB, Max: ${maxMemory}MB")
}

/**
 * Measures the execution time of a given block of code and logs it.
 */
inline fun benchmark(block: () -> Unit) {
    val start = SystemClock.elapsedRealtime()
    block()
    val end = SystemClock.elapsedRealtime()
    dLog("Execution time: ${end - start} ms")
}