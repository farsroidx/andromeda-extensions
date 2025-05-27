@file:Suppress("unused")

package ir.farsroidx.m31

import android.os.StrictMode
import android.view.Choreographer

// TODO: Debug =========================================================================== Debug ===

/**
 * Initializes and applies strict mode policies for debugging only once.
 * This prevents redundant calls from resetting the policies multiple times.
 */
fun initDebugMode() {

    StrictMode.setVmPolicy(
        StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build()
    )

    StrictMode.setThreadPolicy(
        StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build()
    )

    dLog("Application is running in debug mode...")
}

/**
 * Starts monitoring the FPS (Frames Per Second) of the application.
 */
fun startFpsMonitoring() {

    if (fpsCallback != null) return // Prevent multiple instances

    fpsCallback = object : Choreographer.FrameCallback {

        var lastTimeNanos: Long = 0

        var frameCount = 0

        override fun doFrame(frameTimeNanos: Long) {

            if (lastTimeNanos == 0L) {
                lastTimeNanos = frameTimeNanos
            }

            val diff = frameTimeNanos - lastTimeNanos

            frameCount++

            if (diff >= 1_000_000_000) {

                val fps = frameCount * 1_000_000_000 / diff

                dLog("Current FPS: $fps")

                lastTimeNanos = frameTimeNanos

                frameCount = 0
            }

            fpsCallback?.let { Choreographer.getInstance().postFrameCallback(it) }
        }
    }

    fpsCallback?.let { Choreographer.getInstance().postFrameCallback(it) }
}

/**
 * Stops the FPS monitoring by removing the callback from Choreographer.
 */
fun stopFpsMonitoring() {
    fpsCallback?.let { Choreographer.getInstance().removeFrameCallback(it) }
    fpsCallback = null
}

