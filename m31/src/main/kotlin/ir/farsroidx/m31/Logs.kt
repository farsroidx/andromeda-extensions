@file:Suppress("unused")

package ir.farsroidx.m31

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
 * This is only enabled when `BuildConfig.DEBUG` is true.
 *
 * @param logs The logs to be printed.
 */
fun vLog(vararg logs: Any?) {
    if (BuildConfig.DEBUG) { logs.forEach { log -> Log.v(TAG, log.toString()) } }
}

/**
 * Logs the provided logs at the info log level.
 * This is only enabled when `BuildConfig.DEBUG` is true.
 *
 * @param logs The logs to be printed.
 */
fun iLog(vararg logs: Any?) {
    if (BuildConfig.DEBUG) { logs.forEach { log -> Log.i(TAG, log.toString()) } }
}

/**
 * Logs the provided logs at the debug log level.
 * This is only enabled when `BuildConfig.DEBUG` is true.
 *
 * @param logs The logs to be printed.
 */
fun dLog(vararg logs: Any?) {
    if (BuildConfig.DEBUG) { logs.forEach { log -> Log.d(TAG, log.toString()) } }
}

/**
 * Logs the provided logs at the warning log level.
 * This is only enabled when `BuildConfig.DEBUG` is true.
 *
 * @param logs The logs to be printed.
 */
fun wLog(vararg logs: Any?) {
    if (BuildConfig.DEBUG) { logs.forEach { log -> Log.w(TAG, log.toString()) } }
}

/**
 * Logs the provided logs at the error log level.
 * This is only enabled when `BuildConfig.DEBUG` is true.
 *
 * @param logs The logs to be printed.
 */
fun eLog(vararg logs: Any?) {
    if (BuildConfig.DEBUG) { logs.forEach { log -> Log.e(TAG, log.toString()) } }
}