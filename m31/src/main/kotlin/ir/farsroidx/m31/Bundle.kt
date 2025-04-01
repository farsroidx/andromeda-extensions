@file:Suppress("unused")

package ir.farsroidx.m31

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

// TODO: Bundle ========================================================================= Bundle ===

/**
 * Retrieves a Serializable object from the Intent.
 *
 * For Android versions lower than TIRAMISU (API 33), it uses the deprecated `getSerializableExtra`.
 * For Android versions TIRAMISU (API 33) and higher, it uses the newer `getSerializableExtra(key, class)` method.
 *
 * @param key The key associated with the Serializable object.
 * @return The Serializable object associated with the key, or null if not found.
 */
@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Intent.getSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.getSerializableExtra(key, T::class.java)
    else
        this.getSerializableExtra(key) as? T
}

/**
 * Retrieves a Parcelable object from the Intent.
 *
 * For Android versions lower than TIRAMISU (API 33), it uses the deprecated `getParcelableExtra`.
 * For Android versions TIRAMISU (API 33) and higher, it uses the newer `getParcelableExtra(key, class)` method.
 *
 * @param key The key associated with the Parcelable object.
 * @return The Parcelable object associated with the key, or null if not found.
 */
@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.getParcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelableExtra(key, T::class.java)
    } else {
        this.getParcelableExtra(key)
    }
}

/**
 * Retrieves an ArrayList of Parcelable objects from the Intent.
 *
 * For Android versions lower than TIRAMISU (API 33), it uses the deprecated `getParcelableArrayListExtra`.
 * For Android versions TIRAMISU (API 33) and higher, it uses the newer `getParcelableArrayListExtra(key, class)` method.
 *
 * @param key The key associated with the ArrayList of Parcelable objects.
 * @return The ArrayList of Parcelable objects associated with the key, or null if not found.
 */
@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.getParcelableArrayList(key: String): ArrayList<T>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelableArrayListExtra(key, T::class.java)
    } else {
        this.getParcelableArrayListExtra(key)
    }
}

/**
 * Retrieves a Serializable object from the Bundle.
 *
 * For Android versions lower than TIRAMISU (API 33), it uses the deprecated `getSerializable`.
 * For Android versions TIRAMISU (API 33) and higher, it uses the newer `getSerializable(key, class)` method.
 *
 * @param key The key associated with the Serializable object.
 * @return The Serializable object associated with the key, or null if not found.
 */
@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Bundle.getSerializableExtras(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.getSerializable(key, T::class.java)
    else
        this.getSerializable(key) as? T
}

/**
 * Retrieves a Parcelable object from the Bundle.
 *
 * For Android versions lower than TIRAMISU (API 33), it uses the deprecated `getParcelable`.
 * For Android versions TIRAMISU (API 33) and higher, it uses the newer `getParcelable(key, class)` method.
 *
 * @param key The key associated with the Parcelable object.
 * @return The Parcelable object associated with the key, or null if not found.
 */
@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Bundle.getParcelableExtras(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelable(key, T::class.java)
    } else {
        this.getParcelable(key)
    }
}

/**
 * Retrieves an ArrayList of Parcelable objects from the Bundle.
 *
 * For Android versions lower than TIRAMISU (API 33), it uses the deprecated `getParcelableArrayList`.
 * For Android versions TIRAMISU (API 33) and higher, it uses the newer `getParcelableArrayList(key, class)` method.
 *
 * @param key The key associated with the ArrayList of Parcelable objects.
 * @return The ArrayList of Parcelable objects associated with the key, or null if not found.
 */
@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Bundle.getParcelableArrayListExtras(key: String): ArrayList<T>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelableArrayList(key, T::class.java)
    } else {
        this.getParcelableArrayList(key)
    }
}

/**
 * Extension function to safely get a String from the Bundle.
 * If the key does not exist or the value is null, it returns the default value.
 *
 * @param key The key for retrieving the String.
 * @param defaultValue The value to return if the key is not found or the value is null. Default is an empty string.
 * @return The String associated with the key, or the default value if not found.
 */
fun Bundle.getStringSafe(key: String, defaultValue: String = ""): String {
    return getString(key) ?: defaultValue
}

/**
 * Extension function to safely get an Int from the Bundle.
 * If the key does not exist, it returns the default value.
 *
 * @param key The key for retrieving the Int.
 * @param defaultValue The value to return if the key is not found. Default is 0.
 * @return The Int associated with the key, or the default value if not found.
 */
fun Bundle.getIntSafe(key: String, defaultValue: Int = 0): Int {
    return getInt(key, defaultValue)
}

/**
 * Extension function to safely get a Boolean from the Bundle.
 * If the key does not exist, it returns the default value.
 *
 * @param key The key for retrieving the Boolean.
 * @param defaultValue The value to return if the key is not found. Default is false.
 * @return The Boolean associated with the key, or the default value if not found.
 */
fun Bundle.getBooleanSafe(key: String, defaultValue: Boolean = false): Boolean {
    return getBoolean(key, defaultValue)
}

/**
 * Extension function to safely get a Long from the Bundle.
 * If the key does not exist, it returns the default value.
 *
 * @param key The key for retrieving the Long.
 * @param defaultValue The value to return if the key is not found. Default is 0L.
 * @return The Long associated with the key, or the default value if not found.
 */
fun Bundle.getLongSafe(key: String, defaultValue: Long = 0L): Long {
    return getLong(key, defaultValue)
}

/**
 * Extension function to add a String value to the Bundle safely.
 *
 * @param key The key to associate the String with.
 * @param value The String value to be added to the Bundle.
 * @return The same Bundle with the added value.
 */
fun Bundle.putStringSafe(key: String, value: String?): Bundle {
    putString(key, value ?: "")  // If the value is null, it will insert an empty string.
    return this
}

/**
 * Extension function to add an Int value to the Bundle safely.
 *
 * @param key The key to associate the Int with.
 * @param value The Int value to be added to the Bundle.
 * @return The same Bundle with the added value.
 */
fun Bundle.putIntSafe(key: String, value: Int): Bundle {
    putInt(key, value)
    return this
}

/**
 * Extension function to add a Boolean value to the Bundle safely.
 *
 * @param key The key to associate the Boolean with.
 * @param value The Boolean value to be added to the Bundle.
 * @return The same Bundle with the added value.
 */
fun Bundle.putBooleanSafe(key: String, value: Boolean): Bundle {
    putBoolean(key, value)
    return this
}

/**
 * Extension function to add a Long value to the Bundle safely.
 *
 * @param key The key to associate the Long with.
 * @param value The Long value to be added to the Bundle.
 * @return The same Bundle with the added value.
 */
fun Bundle.putLongSafe(key: String, value: Long): Bundle {
    putLong(key, value)
    return this
}

/**
 * Extension function to remove a key from the Bundle if it exists.
 *
 * @param key The key to be removed from the Bundle.
 */
fun Bundle.removeKey(key: String) {
    remove(key)
}