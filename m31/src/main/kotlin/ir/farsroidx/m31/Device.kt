@file:Suppress(
    "unused", "ObsoleteSdkInt", "FunctionName", "DEPRECATION",
    "HardwareIds", "SpellCheckingInspection"
)

package ir.farsroidx.m31

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.KeyguardManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.hardware.Camera
import android.hardware.SensorManager
import android.location.LocationManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.nfc.NfcAdapter
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.os.StatFs
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.util.Locale

// TODO: Device ========================================================================= Device ===

/**
 * This method obtains the ANDROID_ID. If the retrieved value is invalid
 * (null, empty, or the known default invalid value), it requires the `READ_PHONE_STATE` permission.
 *
 * 🔹 **Permission Required:**
 * Ensure that the `Manifest.permission.READ_PHONE_STATE` permission is granted before calling this method,
 * otherwise, it will throw a `SecurityException`.
 *
 * @throws SecurityException If the required permission is not granted.
 * @throws IllegalStateException If no valid device identifier is available.
 * @return A valid unique device identifier.
 */
@SuppressLint("HardwareIds")
@RequiresPermission(Manifest.permission.READ_PHONE_STATE, conditional = true)
fun Context.uniqueAndroidId(): String {

    val androidId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

    if (!androidId.isNullOrEmpty() && androidId != "9774d56d682e549c")
        return androidId

    val hasPermission = Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ==
        PackageManager.PERMISSION_GRANTED

    if (!hasPermission) {
        throw SecurityException("Permission READ_PHONE_STATE is required to obtain a unique device ID")
    }

    throw IllegalStateException("Unable to retrieve a valid Android ID.")
}

/**
 * Android 5 (Lollipop) - API 21
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP)
fun isAndroid5AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)

/**
 * Android 6 (Marshmallow) - API 23
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
fun isAndroid6AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

/**
 * Android 7 (Nougat) - API 24
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
fun isAndroid7AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)

/**
 * Android 7.1 (Nougat MR1) - API 25
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N_MR1)
fun isAndroid7_1AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)

/**
 * Android 8 (Oreo) - API 26
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
fun isAndroid8AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)

/**
 * Android 8.1 (Oreo MR1) - API 27
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O_MR1)
fun isAndroid8_1AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)

/**
 * Android 9 (Pie) - API 28
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
fun isAndroid9AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)

/**
 * Android 10 (Q) - API 29
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
fun isAndroid10AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)

/**
 * Android 11 (R) - API 30
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
fun isAndroid11AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)

/**
 * Android 12 (S) - API 31
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun isAndroid12AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)

/**
 * Android 12L (Sv2) - API 32
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S_V2)
fun isAndroid12LAndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2)

/**
 * Android 13 (Tiramisu) - API 33
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
fun isAndroid13AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)

/**
 * Android 14 (UpsideDownCake) - API 34
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun isAndroid14AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)

/**
 * Android 15 (VanillaIceCream) - API 35
 */
@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.VANILLA_ICE_CREAM)
fun isAndroid15AndAbove() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM)

/**
 * Vibrates the device for the given duration in milliseconds.
 * The function adjusts for devices with different Android versions.
 *
 * For devices running Android 8 (API level 26) or higher, it uses the newer VibrationEffect.
 * For devices below Android 8, it uses the older Vibrator API.
 *
 * Example:
 * ```kotlin
 * vibrateDevice(100) // Vibrates for 100 milliseconds
 * ```
 *
 * @param milliseconds The duration of vibration in milliseconds. Default is 20ms.
 *
 * @requiresPermission [Manifest.permission.VIBRATE] to function properly.
 */
@RequiresPermission(Manifest.permission.VIBRATE, conditional = true)
fun Context.vibrateDevice(milliseconds: Long = 20) {

    // Accessing the appropriate vibrator service based on Android version
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    // Handle vibration effect based on Android version
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Android Oreo (API 26) and above use VibrationEffect
        vibrator.vibrate(
            VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE)
        )
    } else {
        // Devices below Android Oreo (API 26) use the deprecated vibrate method
        @Suppress("DEPRECATION")
        vibrator.vibrate(milliseconds)
    }
}


/**
 * Check if the device is connected to the internet.
 * @return true if the device is online, false if offline.
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE, conditional = true)
fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    // Check for Android M and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    } else {
        // For lower versions, use activeNetworkInfo
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}

/**
 * Check if the device has an active internet connection.
 * @return true if connected to the internet, false otherwise.
 */
@RequiresApi(Build.VERSION_CODES.M)
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE, conditional = true)
fun Context.isConnectedToInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}

/**
 * Check if the device is connected to Wi-Fi.
 * @return true if connected to Wi-Fi, false otherwise.
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE, conditional = true)
fun Context.isConnectedToWifi(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
    return networkInfo?.type == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected
}

/**
 * Check if the device is connected to mobile data.
 * @return true if connected to mobile data, false otherwise.
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE, conditional = true)
fun Context.isConnectedToMobileData(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
    return networkInfo?.type == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected
}

/**
 * Get the current battery level of the device.
 * @return battery level in percentage (0-100).
 */
fun Context.getBatteryLevel(): Int {
    val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    val level = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    return level
}

/**
 * Get the current battery percentage of the device.
 * @return Battery percentage as an integer (0-100). Returns -1 if unable to retrieve battery info.
 */
fun Context.getBatteryPercentage(): Int {
    val intent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
    val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
    return if (level != -1 && scale != -1) {
        (level / scale.toFloat() * 100).toInt()
    } else {
        -1 // Return -1 if unable to get battery level
    }
}

/**
 * Get the manufacturer and model of the device.
 * @return A string containing the manufacturer and model, e.g. "Samsung Galaxy S21".
 */
fun Context.getDeviceModel(): String {
    return "${Build.MANUFACTURER} ${Build.MODEL}"
}

/**
 * Get the width and height of the device's screen.
 * @return A Pair containing width and height in pixels (width, height).
 */
fun Context.getScreenSize(): Pair<Int, Int> {
    val displayMetrics = DisplayMetrics()
    val display = (this.getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager).defaultDisplay
    display.getMetrics(displayMetrics)
    return displayMetrics.widthPixels to displayMetrics.heightPixels
}

/**
 * Check if the device has a specific sensor.
 * @param sensorType The type of sensor to check for (e.g., Sensor.TYPE_ACCELEROMETER).
 * @return true if the sensor is available, false otherwise.
 */
fun Context.hasSensor(sensorType: Int): Boolean {
    val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensors = sensorManager.getSensorList(sensorType)
    return sensors.isNotEmpty()
}

/**
 * Get the version of the device's operating system.
 * @return The version string, e.g. "Android 12".
 */
fun Context.getOsVersion(): String {
    return "Android ${Build.VERSION.RELEASE}"
}

/**
 * Get the screen density of the device.
 * @return The screen density as a float (e.g., 2.0 for medium density).
 */
fun Context.getScreenDensity(): Float {
    val displayMetrics = DisplayMetrics()
    val display = (this.getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager).defaultDisplay
    display.getMetrics(displayMetrics)
    return displayMetrics.density
}

/**
 * Check if the device is currently in landscape mode.
 * @return true if the device is in landscape, false if in portrait.
 */
fun Context.isLandscape(): Boolean {
    val orientation = resources.configuration.orientation
    return orientation == Configuration.ORIENTATION_LANDSCAPE
}

/**
 * Get the available RAM on the device.
 * @return The available RAM in megabytes.
 */
fun Context.getAvailableRAM(): Long {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val memoryInfo = ActivityManager.MemoryInfo()
    activityManager.getMemoryInfo(memoryInfo)
    return memoryInfo.availMem / 1024 / 1024 // Convert from bytes to MB
}

/**
 * Get the total RAM on the device.
 * @return The total RAM in megabytes.
 */
fun Context.getTotalRAM(): Long {
    val memoryInfo = ActivityManager.MemoryInfo()
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    activityManager.getMemoryInfo(memoryInfo)
    return memoryInfo.totalMem / 1024 / 1024 // Convert from bytes to MB
}

/**
 * Check the available internal storage space on the device.
 * @return The available storage space in megabytes.
 */
fun Context.getAvailableInternalStorage(): Long {
    val path = File(filesDir.absolutePath)
    val stat = StatFs(path.absolutePath)
    val availableBytes = stat.availableBlocksLong * stat.blockSizeLong
    return availableBytes / 1024 / 1024 // Convert from bytes to MB
}

/**
 * Get the total internal storage space on the device.
 * @return The total storage space in megabytes.
 */
fun Context.getTotalInternalStorage(): Long {
    val path = File(filesDir.absolutePath)
    val stat = StatFs(path.absolutePath)
    val totalBytes = stat.blockCountLong * stat.blockSizeLong
    return totalBytes / 1024 / 1024 // Convert from bytes to MB
}

/**
 * Check the available external storage space on the device.
 * @return The available storage space in megabytes.
 */
fun Context.getAvailableExternalStorage(): Long {
    val path = Environment.getExternalStorageDirectory()
    val stat = StatFs(path.absolutePath)
    val availableBytes = stat.availableBlocksLong * stat.blockSizeLong
    return availableBytes / 1024 / 1024 // Convert from bytes to MB
}

/**
 * Get the total external storage space on the device.
 * @return The total storage space in megabytes.
 */
fun Context.getTotalExternalStorage(): Long {
    val path = Environment.getExternalStorageDirectory()
    val stat = StatFs(path.absolutePath)
    val totalBytes = stat.blockCountLong * stat.blockSizeLong
    return totalBytes / 1024 / 1024 // Convert from bytes to MB
}

/**
 * Check if there is enough free space on the device's internal or external storage.
 * @param requiredSpace The required space in megabytes.
 * @param isExternal Whether to check external storage (true) or internal storage (false).
 * @return true if enough space is available, false otherwise.
 */
fun Context.hasEnoughFreeStorage(requiredSpace: Long, isExternal: Boolean = false): Boolean {
    val path = if (isExternal) Environment.getExternalStorageDirectory() else filesDir
    val stat = StatFs(path.absolutePath)
    val availableBytes = stat.availableBlocksLong * stat.blockSizeLong
    return availableBytes / 1024 / 1024 >= requiredSpace
}

/**
 * Get the battery health status of the device.
 * @return The battery health status, e.g., "Good", "Overheat", "Dead", etc.
 */
fun Context.getBatteryHealth(): String {
    val intent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    val health = intent?.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN)
    return when (health) {
        BatteryManager.BATTERY_HEALTH_GOOD     -> "Good"
        BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
        BatteryManager.BATTERY_HEALTH_DEAD     -> "Dead"
        BatteryManager.BATTERY_HEALTH_UNKNOWN  -> "Unknown"
        else                                   -> "Unknown"
    }
}

/**
 * Check if the device is currently charging.
 * @return true if the device is charging, false otherwise.
 */
fun Context.isCharging(): Boolean {
    val intent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
    return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
}

/**
 * Get the current screen brightness level of the device.
 * @return The brightness level as an integer (0-255).
 */
@IntRange(0, 255)
fun Context.getScreenBrightness(): Int {
    return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
}

/**
 * Set the screen brightness level of the device.
 * @param brightness The brightness level to set (0-255).
 */
fun Context.setScreenBrightness(@IntRange(0, 255) brightness: Int) {
    Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
}

/**
 * Get the current language of the device.
 * @return The language code of the device (e.g., "en", "fa").
 */
fun Context.getCurrentLanguage(): String {
    return Locale.getDefault().language
}

/**
 * Check if the device is currently in low power mode.
 * @return true if the device is in low power mode, false otherwise.
 */
fun Context.isLowPowerMode(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isPowerSaveMode
    }
    return false
}

/**
 * Check if the device supports Bluetooth.
 * @return true if Bluetooth is supported, false otherwise.
 */
fun Context.hasBluetooth(): Boolean {
    return BluetoothAdapter.getDefaultAdapter() != null
}

/**
 * Check if Bluetooth is enabled on the device.
 * @return true if Bluetooth is enabled, false otherwise.
 */
fun isBluetoothEnabled(): Boolean {
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    return bluetoothAdapter?.isEnabled == true
}

/**
 * Request to enable Bluetooth on the device.
 * @return an Intent to request enabling Bluetooth.
 */
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT, conditional = true)
fun enableBluetooth(): Intent {
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    return Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE).apply {
        bluetoothAdapter?.takeIf { !it.isEnabled }?.enable()
    }
}

/**
 * Check if the device supports NFC (Near Field Communication).
 * @return true if NFC is supported, false otherwise.
 */
fun Context.hasNFC(): Boolean {
    return NfcAdapter.getDefaultAdapter(this) != null
}

/**
 * Check if the device's GPS is enabled.
 * @return true if GPS is enabled, false otherwise.
 */
fun Context.isGpsEnabled(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

/**
 * Request for the user's location permission.
 * @return a boolean indicating if location services are enabled.
 */
fun Context.requestGpsPermission(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER).also {
        if (!it) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }
}

/**
 * Check if the device is in Airplane Mode.
 * @return true if Airplane Mode is enabled, false otherwise.
 */
fun Context.isAirplaneModeOn(): Boolean {
    return Settings.System.getInt(contentResolver, Settings.System.AIRPLANE_MODE_ON, 0) != 0
}

/**
 * Get the current orientation of the device.
 * @return the orientation (either Configuration.ORIENTATION_LANDSCAPE or Configuration.ORIENTATION_PORTRAIT).
 */
fun Context.getDeviceOrientation(): Int {
    val orientation = resources.configuration.orientation
    return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Configuration.ORIENTATION_LANDSCAPE
    } else {
        Configuration.ORIENTATION_PORTRAIT
    }
}

/**
 * Check if the device is currently in dark mode.
 * @return true if the device is in dark mode, false otherwise.
 */
fun Context.isDarkMode(): Boolean {
    val currentMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return currentMode == Configuration.UI_MODE_NIGHT_YES
}

/**
 * Toggle the screen orientation lock on or off.
 * @param isLocked true to lock orientation, false to unlock.
 */
fun Context.setOrientationLock(isLocked: Boolean) {
    val orientationMode = if (isLocked) {
        Settings.System.ACCELEROMETER_ROTATION
    } else {
        Settings.System.ACCELEROMETER_ROTATION
    }
    Settings.System.putInt(contentResolver, orientationMode, if (isLocked) 0 else 1)
}

/**
 * Get the current system volume level.
 * @return the system volume level between 0 and the maximum volume.
 */
fun Context.getSystemVolume(): Int {
    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
}

/**
 * Set the system volume level.
 * @param volume the volume level between 0 and the maximum volume.
 */
fun Context.setSystemVolume(volume: Int) {
    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0)
}

/**
 * Check if the device is currently locked.
 * @return true if the device is locked, false otherwise.
 */
fun Context.isDeviceLocked(): Boolean {
    val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    return keyguardManager.inKeyguardRestrictedInputMode()
}

/**
 * Get the screen timeout duration in milliseconds.
 * @return the screen timeout duration in milliseconds.
 */
fun Context.getScreenTimeout(): Int {
    return Settings.System.getInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 30000)
}

/**
 * Set the screen timeout duration.
 * @param timeout the screen timeout duration in milliseconds.
 */
fun Context.setScreenTimeout(timeout: Int) {
    Settings.System.putInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, timeout)
}

/**
 * Get the IMEI (International Mobile Equipment Identity) of the device.
 * @return the IMEI as a string, or null if not available.
 */
@RequiresPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", conditional = true)
fun Context.getDeviceIMEI(): String? {
    val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        telephonyManager.imei
    } else {
        telephonyManager.deviceId
    }
}

/**
 * Get the device's IMEI number.
 * @return IMEI number or empty string if not available.
 */
@RequiresPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", conditional = true)
fun Context.getIMEINumber(): String {
    val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
        telephonyManager.deviceId
    } else {
        ""
    }
}

/**
 * Check if the device is rooted.
 * @return true if the device is rooted, false otherwise.
 */
fun isDeviceRooted(): Boolean {
    return (
        File("/system/app/Superuser.apk").exists() ||
        File("/system/xbin/su").exists() ||
        File("/system/bin/su").exists() ||
        File("/data/local/bin/su").exists()
    )
}

/**
 * Check if the device has a front camera.
 * @return true if the device has a front camera, false otherwise.
 */
fun Context.hasFrontCamera(): Boolean {
    val numberOfCameras = Camera.getNumberOfCameras()
    for (i in 0 until numberOfCameras) {
        val cameraInfo = Camera.CameraInfo()
        Camera.getCameraInfo(i, cameraInfo)
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            return true
        }
    }
    return false
}