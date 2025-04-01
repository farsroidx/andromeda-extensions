@file:Suppress("unused")

package ir.farsroidx.m31

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import androidx.core.net.toUri

// TODO: Intent ========================================================================= Intent ===

/**
 * Retrieves the query parameter associated with the given key from the Intent's URI.
 *
 * If the Intent is null or the query parameter is not present, an empty string will be returned.
 *
 * @param key The query parameter key to retrieve.
 * @return The query parameter value associated with the key, or an empty string if not found or the Intent is null.
 */
fun Intent?.getQueryParameter(key: String): String =
    this?.data?.getQueryParameter(key) ?: ""

/**
 * Clears the activity history stack and starts a new task with the specified flags.
 *
 * This method adds the following flags to the Intent:
 * - `Intent.FLAG_ACTIVITY_NEW_TASK`: Starts a new task.
 * - `Intent.FLAG_ACTIVITY_CLEAR_TASK`: Clears any existing task that is already running.
 *
 * You can also pass additional flags through the `additionalFlags` parameter.
 *
 * Example:
 * ```kotlin
 * val intent = Intent(this, SomeActivity::class.java)
 * intent.clearLastActivities()
 * startActivity(intent)
 * ```
 *
 * @param additionalFlags Optional additional flags to be added to the Intent.
 */
fun Intent.clearLastActivities(additionalFlags: Int = 0) {
    flags = additionalFlags or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
}

/**
 * Opens the application settings screen where the user can modify app-specific permissions and settings.
 *
 * Example usage:
 * ```kotlin
 * context.openApplicationSettings()
 * ```
 */
fun Context.openApplicationSettings() {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

/**
 * Open the app's settings screen.
 */
fun Context.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = "package:$packageName".toUri()
    startActivity(intent)
}

/**
 * Start an activity using an intent.
 * @param activityClass The class of the activity to start.
 */
fun Context.launchActivity(activityClass: Class<out Activity>) {
    val intent = Intent(this, activityClass)
    startActivity(intent)
}

/**
 * Start an activity for result.
 * @param activityClass The class of the activity to start.
 * @param requestCode The request code for the result.
 */
fun Activity.launchActivityForResult(activityClass: Class<out Activity>, requestCode: Int) {
    val intent = Intent(this, activityClass)
    startActivityForResult(intent, requestCode)
}

/**
 * Launch email client to send an email.
 * @param email The recipient's email address.
 * @param subject The subject of the email.
 * @param body The body of the email.
 */
fun Context.sendEmail(email: String, subject: String = "", body: String = "") {
    val intent = Intent(Intent.ACTION_SENDTO, "mailto:$email".toUri())
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, body)
    startActivity(Intent.createChooser(intent, "Send Email"))
}

/**
 * Open a URL in the browser.
 * @param url The URL to open.
 */
fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    startActivity(intent)
}

/**
 * Share text using the available sharing apps.
 * @param text The text to share.
 */
fun Context.shareText(text: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    startActivity(Intent.createChooser(intent, "Share"))
}

/**
 * Launch the gallery app to pick an image.
 * @param requestCode The request code for the result.
 */
fun Activity.pickImageFromGallery(requestCode: Int) {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    startActivityForResult(intent, requestCode)
}

/**
 * Open the dialer with a pre-filled phone number.
 * @param phoneNumber The phone number to dial.
 */
fun Context.openDialer(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = "tel:$phoneNumber".toUri()
    startActivity(intent)
}

/**
 * Launch the camera app to take a photo.
 * @param requestCode The request code for the result.
 */
fun Activity.launchCameraToTakePhoto(requestCode: Int) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, requestCode)
}

/**
 * Launch the contacts app to pick a contact.
 * @param requestCode The request code for the result.
 */
fun Activity.pickContact(requestCode: Int) {
    val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
    startActivityForResult(intent, requestCode)
}

/**
 * Open a location on Google Maps.
 * @param latitude The latitude of the location.
 * @param longitude The longitude of the location.
 */
fun Context.openLocationOnMaps(latitude: Double, longitude: Double) {
    val uri = "geo:$latitude,$longitude".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.google.android.apps.maps")
    startActivity(intent)
}

/**
 * Check if an Intent can be resolved (if there's an app to handle the intent).
 * @param intent The intent to check.
 * @return true if the intent can be resolved, false otherwise.
 */
fun Context.canResolveIntent(intent: Intent): Boolean {
    return packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).isNotEmpty()
}

/**
 * Start a service.
 * @param serviceClass The class of the service to start.
 */
fun Context.startService(serviceClass: Class<out Service>) {
    val intent = Intent(this, serviceClass)
    startService(intent)
}

/**
 * Stop a running service.
 * @param serviceClass The class of the service to stop.
 */
fun Context.stopService(serviceClass: Class<out Service>) {
    val intent = Intent(this, serviceClass)
    stopService(intent)
}