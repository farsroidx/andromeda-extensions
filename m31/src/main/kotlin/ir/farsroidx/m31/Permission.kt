@file:Suppress("unused")

package ir.farsroidx.m31

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// TODO: Permission ================================================================= Permission ===

/**
 * Checks if the specified permissions are granted for the current context.
 *
 * If the Android version is below Marshmallow (API 23), assumes permissions are granted.
 *
 * @param permissions The list of permissions to check (e.g., [android.Manifest.permission.CAMERA]).
 * @return true if all permissions are granted, false otherwise.
 */
fun Context.arePermissionsGranted(vararg permissions: String): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
    // Check if all permissions are granted
    return permissions.all { permission ->
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }
}

/**
 * Checks if the specified permissions are denied for the current context.
 *
 * If the Android version is below Marshmallow (API 23), assumes permissions are not denied.
 *
 * @param permissions The list of permissions to check (e.g., [android.Manifest.permission.CAMERA]).
 * @return true if any of the permissions are denied, false if all are granted.
 */
fun Context.arePermissionsDenied(vararg permissions: String): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false
    // Check if any permission is denied
    return permissions.any { permission ->
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
    }
}

/**
 * Requests the specified permissions for the current activity.
 *
 * This method supports multiple permissions passed as a `vararg` argument.
 *
 * Example:
 * ```kotlin
 * val permissions = arrayOf(
 *     Manifest.permission.CAMERA,
 *     Manifest.permission.ACCESS_FINE_LOCATION
 * )
 * requestPermissions(*permissions, requestCode = 1)
 * ```
 *
 * @param permissions The list of permissions to request (e.g., [android.Manifest.permission.CAMERA]).
 * @param requestCode The request code to identify the permission request result.
 */
fun Activity.requestPermissions(requestCode: Int, vararg permissions: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.requestPermissions(permissions, requestCode)
    }
}

/**
 * Checks if the application has permission to write to external storage.
 * @return true if permission is granted, false otherwise.
 */
fun Context.checkStoragePermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this, Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
}

/**
 * Requests the storage permission if it is not granted already.
 * @param requestCode The request code to identify the permission request.
 */
fun Activity.requestStoragePermission(requestCode: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode
        )
    }
}

/**
 * Requests the storage permission in a scoped way for Android 10 and above.
 * @param requestCode The request code to identify the permission request.
 */
@RequiresApi(Build.VERSION_CODES.R)
fun Activity.requestScopedStoragePermission(requestCode: Int) {
    // Requests the permission for scoped storage
    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
    startActivityForResult(intent, requestCode)
}

/**
 * Returns an intent to open a file picker for selecting a file.
 * @return Intent to pick a file from the device.
 */
fun Context.getFilePickerIntent(): Intent {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.type = "*/*" // Allows all types of files
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    return intent
}

/**
 * Gets the URI for the external images folder.
 * @return URI for the external images folder.
 */
fun Context.getPicturesFolderUri(): Uri {
    return MediaStore.Images.Media.EXTERNAL_CONTENT_URI
}

/**
 * Saves an image to the device's gallery.
 * @param imageUri URI of the image to save.
 */
fun Context.saveImageToGallery(imageUri: Uri) {
    val contentResolver = contentResolver
    val newUri = contentResolver.insert(getPicturesFolderUri(), ContentValues())
    contentResolver.openOutputStream(newUri!!)?.use { outputStream ->
        val inputStream = contentResolver.openInputStream(imageUri)
        inputStream?.copyTo(outputStream)
    }
}

/**
 * Checks if the app has the MANAGE_EXTERNAL_STORAGE permission for Android 11 and above.
 * @return true if the permission is granted, false otherwise.
 */
@RequiresApi(Build.VERSION_CODES.R)
fun Context.checkManageStoragePermission(): Boolean {
    return Environment.isExternalStorageManager()
}

/**
 * Requests the MANAGE_EXTERNAL_STORAGE permission for Android 11 and above.
 * @param requestCode The request code to identify the permission request.
 */
fun Activity.requestManageStoragePermission(requestCode: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        startActivityForResult(intent, requestCode)
    }
}