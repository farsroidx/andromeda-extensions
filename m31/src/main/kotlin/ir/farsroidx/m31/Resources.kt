@file:Suppress("unused", "DiscouragedApi")

package ir.farsroidx.m31

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnimRes
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import org.xmlpull.v1.XmlPullParser
import java.io.IOException
import java.util.Properties

// TODO: Resources =================================================================== Resources ===

fun Context.getColorRes(@ColorRes resId: Int): Int =
    ContextCompat.getColor(this, resId)

fun Context.getStringRes(@StringRes resId: Int, vararg formatArgs: Any): String =
    getString(resId, formatArgs)

fun Context.getDrawableRes(@DrawableRes resId: Int): Drawable? =
    ContextCompat.getDrawable(this, resId)

fun Context.getStringArrayRes(@ArrayRes resId: Int): StringArray =
    resources.getStringArray(resId)

fun Context.getIntArrayRes(@ArrayRes resId: Int): IntArray =
    resources.getIntArray(resId)

fun Context.getTypefaceRes(@FontRes resId: Int) =
    ResourcesCompat.getFont(this, resId)

fun Context.getDimensionRes(@DimenRes resId: Int) =
    resources.getDimension(resId)

fun Context.getBooleanRes(@BoolRes resId: Int) =
    resources.getBoolean(resId)

fun Context.getAnimationRes(@AnimRes resId: Int) =
    resources.getAnimation(resId)

fun Context.getIntegerRes(@IntegerRes resId: Int) =
    resources.getInteger(resId)

fun Context.getLayoutRes(@LayoutRes resId: Int) =
    resources.getLayout(resId)

// -------------------------------------------------------------------------------------------------

fun Fragment.getColorRes(@ColorRes resId: Int): Int =
    requireContext().getColorRes(resId)

fun Fragment.getStringRes(@StringRes resId: Int, vararg formatArgs: Any): String =
    requireContext().getStringRes(resId, formatArgs)

fun Fragment.getDrawableRes(@DrawableRes resId: Int): Drawable? =
    requireContext().getDrawableRes(resId)

fun Fragment.getStringArrayRes(@ArrayRes resId: Int): StringArray  =
    requireContext().getStringArrayRes(resId)

fun Fragment.getIntArrayRes(@ArrayRes resId: Int): IntArray =
    requireContext().getIntArrayRes(resId)

fun Fragment.getTypefaceRes(@FontRes resId: Int) =
    requireContext().getTypefaceRes(resId)

fun Fragment.getDimensionRes(@DimenRes resId: Int) =
    requireContext().getDimensionRes(resId)

fun Fragment.getBooleanRes(@BoolRes resId: Int) =
    requireContext().getBooleanRes(resId)

fun Fragment.getAnimationRes(@AnimRes resId: Int) =
    requireContext().getAnimationRes(resId)

fun Fragment.getIntegerRes(@IntegerRes resId: Int) =
    requireContext().getIntegerRes(resId)

fun Fragment.getLayoutRes(@LayoutRes resId: Int) =
    requireContext().getLayoutRes(resId)

// -------------------------------------------------------------------------------------------------

fun View.getColorRes(@ColorRes resId: Int): Int =
    context.getColorRes(resId)

fun View.getStringRes(@StringRes resId: Int, vararg formatArgs: Any): String =
    context.getStringRes(resId, formatArgs)

fun View.getDrawableRes(@DrawableRes resId: Int): Drawable? =
    context.getDrawableRes(resId)

fun View.getStringArrayRes(@ArrayRes resId: Int): StringArray  =
    context.getStringArrayRes(resId)

fun View.getIntArrayRes(@ArrayRes resId: Int): IntArray =
    context.getIntArrayRes(resId)

fun View.getTypefaceRes(@FontRes resId: Int) =
    context.getTypefaceRes(resId)

fun View.getDimensionRes(@DimenRes resId: Int) =
    context.getDimensionRes(resId)

fun View.getBooleanRes(@BoolRes resId: Int) =
    context.getBooleanRes(resId)

fun View.getAnimationRes(@AnimRes resId: Int) =
    context.getAnimationRes(resId)

fun View.getIntegerRes(@IntegerRes resId: Int) =
    context.getIntegerRes(resId)

fun View.getLayoutRes(@LayoutRes resId: Int) =
    context.getLayoutRes(resId)

// -------------------------------------------------------------------------------------------------

/**
 * Reads the content of a file from the `assets` folder and returns it as a `String`.
 *
 * This function attempts to open the specified file, read its content using UTF-8 encoding,
 * and return it as a string. If an exception occurs, it invokes the `exception` callback
 * and returns an empty string.
 *
 * Example:
 * ```kotlin
 * val jsonData = context.getStringFromAssets("data.json")
 * ```
 *
 * @param path The relative path of the file inside the `assets` folder.
 * @param exception A lambda function to handle exceptions that may occur while reading the file.
 * @return The content of the file as a string, or an empty string if an error occurs.
 */
fun Context.getStringFromAssets(
    path: String,
    exception: (IOException) -> Unit = {}
): String {
    return try {
        assets.open(path).bufferedReader(Charsets.UTF_8).use { it.readText() }
    } catch (e: IOException) {
        e.printStackTrace()
        exception(e)
        ""
    }
}

/**
 * Reads the content of a file from the `assets` folder and returns it as a `String`.
 *
 * This function attempts to open the specified file, read its content using UTF-8 encoding,
 * and return it as a string. If an exception occurs, it invokes the `exception` callback
 * and returns an empty string.
 *
 * Example:
 * ```kotlin
 * val jsonData = fragment.getStringFromAssets("data.json")
 * ```
 *
 * @param path The relative path of the file inside the `assets` folder.
 * @param exception A lambda function to handle exceptions that may occur while reading the file.
 * @return The content of the file as a string, or an empty string if an error occurs.
 */
fun Fragment.getStringFromAssets(path: String, exception: (IOException) -> Unit = {}): String =
    requireContext().getStringFromAssets(path, exception)

/**
 * Reads the content of a file from the `assets` folder and returns it as a `String`.
 *
 * This function attempts to open the specified file, read its content using UTF-8 encoding,
 * and return it as a string. If an exception occurs, it invokes the `exception` callback
 * and returns an empty string.
 *
 * Example:
 * ```kotlin
 * val jsonData = viewGroup.getStringFromAssets("data.json")
 * ```
 *
 * @param path The relative path of the file inside the `assets` folder.
 * @param exception A lambda function to handle exceptions that may occur while reading the file.
 * @return The content of the file as a string, or an empty string if an error occurs.
 */
fun ViewGroup.getStringFromAssets(path: String, exception: (IOException) -> Unit = {}): String =
    context.getStringFromAssets(path, exception)

/**
 * Reads the content of a file from the `assets` folder and returns it as a `String`.
 *
 * This function attempts to open the specified file, read its content using UTF-8 encoding,
 * and return it as a string. If an exception occurs, it invokes the `exception` callback
 * and returns an empty string.
 *
 * Example:
 * ```kotlin
 * val jsonData = view.getStringFromAssets("data.json")
 * ```
 *
 * @param path The relative path of the file inside the `assets` folder.
 * @param exception A lambda function to handle exceptions that may occur while reading the file.
 * @return The content of the file as a string, or an empty string if an error occurs.
 */
fun View.getStringFromAssets(path: String, exception: (IOException) -> Unit = {}): String =
    context.getStringFromAssets(path, exception)

// -------------------------------------------------------------------------------------------------

/**
 * Loads a `.properties` file from the `raw` resource directory and returns it as a `Properties` object.
 *
 * This function retrieves the resource identifier using the given file name, opens the raw resource stream,
 * and loads the properties into a `Properties` object. It allows reading key-value pairs from a properties file
 * stored in the `res/raw` directory.
 *
 * Example:
 * ```kotlin
 * val properties = context.getProperties("config")
 * val apiUrl = properties.getProperty("api_url")
 * ```
 *
 * @param name The name of the `.properties` file (without the extension) located in `res/raw`.
 * @return A `Properties` object containing key-value pairs from the specified file.
 * @throws Resources.NotFoundException if the resource cannot be found.
 * @throws IOException if an error occurs while reading the file.
 */
fun Context.getProperties(name: String): Properties {

    val properties = Properties()

    val resourceId = resources.getIdentifier(name, "raw", packageName)

    if (resourceId == 0)
        throw Resources.NotFoundException("Resource '$name' not found in raw folder")

    resources.openRawResource(resourceId).use { inputStream ->
        properties.load(inputStream)
    }

    return properties
}

/**
 * Loads a `.properties` file from the `raw` resource directory and returns it as a `Properties` object.
 *
 * This function retrieves the resource identifier using the given file name, opens the raw resource stream,
 * and loads the properties into a `Properties` object. It allows reading key-value pairs from a properties file
 * stored in the `res/raw` directory.
 *
 * Example:
 * ```kotlin
 * val properties = fragment.getProperties("config")
 * val apiUrl = properties.getProperty("api_url")
 * ```
 *
 * @param name The name of the `.properties` file (without the extension) located in `res/raw`.
 * @return A `Properties` object containing key-value pairs from the specified file.
 * @throws Resources.NotFoundException if the resource cannot be found.
 * @throws IOException if an error occurs while reading the file.
 */
fun Fragment.getProperties(name: String): Properties =
    requireContext().getProperties(name)

// -------------------------------------------------------------------------------------------------

/**
 * Loads an XML resource and returns its `XmlPullParser` for further processing.
 *
 * This function retrieves an XML resource using its resource ID and provides a parser
 * that can be used to read its content.
 *
 * @param resId The resource ID of the XML file to be loaded.
 * @return An `XmlPullParser` instance for parsing the XML resource.
 */
fun Context.getXmlRes(@XmlRes resId: Int): XmlPullParser {
    return resources.getXml(resId)
}

/**
 * Loads an XML resource and returns its `XmlPullParser` for further processing.
 *
 * This function retrieves an XML resource using its resource ID and provides a parser
 * that can be used to read its content.
 *
 * @param resId The resource ID of the XML file to be loaded.
 * @return An `XmlPullParser` instance for parsing the XML resource.
 */
fun Fragment.getXmlRes(@XmlRes resId: Int): XmlPullParser =
    requireContext().getXmlRes(resId)

// -------------------------------------------------------------------------------------------------