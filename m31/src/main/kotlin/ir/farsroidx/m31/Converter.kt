@file:Suppress("unused")

package ir.farsroidx.m31

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Base64
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import androidx.core.graphics.toColorInt

// TODO: Converter =================================================================== Converter ===

/**
 * Extension property to convert an integer value in `dp` (density-independent pixels)
 * to its corresponding value in `px` (pixels) based on the current screen density.
 *
 * Example:
 * ```kotlin
 * val dpValue = 16
 * val pxValue = dpValue.px
 * println(pxValue) // Output will depend on the device's screen density. For example: 48
 * ```
 *
 * @return The corresponding value in pixels (`px`).
 */
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Extension property to convert a float value in `dp` (density-independent pixels)
 * to its corresponding value in `px` (pixels) based on the current screen density.
 *
 * Example:
 * ```kotlin
 * val dpValue = 16F
 * val pxValue = dpValue.px
 * println(pxValue) // Output will depend on the device's screen density. For example: 48.0
 * ```
 *
 * @return The corresponding value in pixels (`px`).
 */
val Float.px: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

/**
 * Extension property to convert an integer value in `px` (pixels)
 * to its corresponding value in `dp` (density-independent pixels) based on the current screen density.
 *
 * Example:
 * ```kotlin
 * val pxValue = 48
 * val dpValue = pxValue.dp
 * println(dpValue) // Output will depend on the device's screen density. For example: 16
 * ```
 *
 * @return The corresponding value in density-independent pixels (`dp`).
 */
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Extension property to convert a float value in `px` (pixels)
 * to its corresponding value in `dp` (density-independent pixels) based on the current screen density.
 *
 * Example:
 * ```kotlin
 * val pxValue = 48F
 * val dpValue = pxValue.dp
 * println(dpValue) // Output will depend on the device's screen density. For example: 16.0
 * ```
 *
 * @return The corresponding value in density-independent pixels (`dp`).
 */
val Float.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

/**
 * Extension property to convert a string (e.g., a color name or hexadecimal string)
 * to its corresponding integer color value.
 *
 * Example:
 * ```kotlin
 * val colorName = "red"
 * val colorValue = colorName.color
 * println(colorValue) // Output will be the color value in integer (e.g., red's value: -65536).
 * ```
 *
 * @return The corresponding color as an integer (e.g., ARGB color value).
 */
val String.color: Int
    get() = this.toColorInt()

/**
 * Converts an HTML string to a [Spanned] object.
 * This allows you to display HTML content in a `TextView` in Android.
 *
 * It uses `Html.fromHtml()` with the appropriate mode depending on the Android version.
 * For Android Nougat (API 24) and above, it uses `FROM_HTML_MODE_LEGACY` to ensure backward compatibility.
 *
 * Example:
 * ```kotlin
 * val htmlString = "<b>Bold Text</b>"
 * val spannable = htmlString.toSpannableFromHtml()
 * textView.text = spannable
 * ```
 *
 * @return A [Spanned] object representing the HTML content.
 */
fun String.toSpannableFromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}

/**
 * Converts a [Spanned] object back to an HTML string.
 * This is useful when you need to convert a `Spanned` object (such as from a TextView) back to HTML for further processing.
 *
 * It uses `Html.toHtml()` with the appropriate mode depending on the Android version.
 * For Android Nougat (API 24) and above, it uses `FROM_HTML_MODE_LEGACY`.
 *
 * Example:
 * ```kotlin
 * val spannedText: Spanned = textView.text
 * val htmlString = spannedText.toHtmlStringFromSpannable()
 * ```
 *
 * @return An HTML string representation of the [Spanned] content.
 */
fun Spanned.toHtmlStringFromSpannable(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.toHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.toHtml(this)
    }
}

/**
 * Formats a numeric value into a string with a comma separator for thousands.
 *
 * Example:
 * ```
 * val number = 1234567890
 * println(number.toCurrencyFormat())  // Output: "1,234,567,890"
 * ```
 *
 * @return A string representation of the number with a thousands separator.
 */
fun Number.toCurrencyFormat(): String = decimalFormat.format(this)

/**
 * Formats a nullable string into a number with a thousands separator.
 * If the string is null, it defaults to 0.
 *
 * Example:
 * ```
 * val str = "1234567890"
 * println(str.toCurrencyFormat())  // Output: "1,234,567,890"
 * ```
 *
 * @return A string representation of the number with a thousands separator.
 */
fun String?.toCurrencyFormat(): String {
    val number = this?.toDoubleOrNull() ?: 0.0
    return decimalFormat.format(number)
}

/**
 * Extracts a substring from the given string, starting at the specified position and reading the specified number of characters.
 *
 * The startPosition is 1-based (i.e., the first character is at position 1).
 * If readRow is not provided, it defaults to 1 character.
 *
 * Example:
 * ```
 * val result = "HelloWorld".subString(1, 5) // "Hello"
 * ```
 *
 * @param startPosition The starting position (1-based index).
 * @param readRow The number of characters to read.
 * @return The extracted substring.
 */
fun String.subString(startPosition: Int, readRow: Int = 1): String {
    return this.substring(startPosition - 1, startPosition - 1 + readRow)
}

/**
 * Extracts a substring from the string and converts it into the specified numeric type (e.g., Int, Long, Float).
 *
 * The startPosition is 1-based (i.e., the first character is at position 1).
 * This method returns the numeric type as defined by T.
 *
 * Example:
 * ```
 * val result = "12345".subStringAs<Int>(1, 3) // 123
 * ```
 *
 * @param startPosition The starting position (1-based index).
 * @param readRow The number of characters to read (default is 1).
 * @throws IllegalArgumentException If the conversion to the required numeric type fails.
 * @return The extracted substring converted to the numeric type T.
 */
inline fun <reified T: Number> String.subStringAs(startPosition: Int, readRow: Int = 1): T {

    val value = this.substring(startPosition, readRow)

    return when(T::class) {

        Byte::class   -> value.toByte()
        Short::class  -> value.toShort()
        Int::class    -> value.toInt()
        Float::class  -> value.toFloat()
        Double::class -> value.toDouble()
        Long::class   -> value.toLong()
        else          -> {
            throw IllegalArgumentException("Unsupported numeric type: ${T::class}")
        }

    } as T
}

/**
 * Extension function to generate MD5 hash of a string.
 *
 * Example:
 * ```
 * val hashedString = "example".md5
 * println(hashedString) // "1a79a4d60de6718e8e5b8e79f87f3b39"
 * ```
 *
 * @return The MD5 hash of the string in hexadecimal format.
 */
val String.md5: String
    get() {
        return try {
            val digest = MessageDigest.getInstance("MD5")
            val messageDigest = digest.digest(this.toByteArray())
            messageDigest.joinToString("") { "%02x".format(it) }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            ""
        }
    }

/**
 * Encodes a string into Base64 format.
 *
 * Example:
 * ```
 * "Hello, World!".encodeBase64() // "SGVsbG8sIFdvcmxkIQ=="
 * ```
 *
 * @return The Base64 encoded string.
 */
fun String.encodeBase64(): String {
    return Base64.encodeToString(this.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
}

/**
 * Decodes a Base64 encoded string back to its original format.
 *
 * Example:
 * ```
 * "SGVsbG8sIFdvcmxkIQ==".decodeBase64() // "Hello, World!"
 * ```
 *
 * @return The decoded original string.
 */
fun String.decodeBase64(): String {
    return String(Base64.decode(this, Base64.NO_WRAP), Charsets.UTF_8)
}

/**
 * Converts a string from UpperCamelCase to snake_case.
 *
 * Example:
 * ```
 * "ExampleVariableName".upperCamelToSnakeCase() // "example_variable_name"
 * ```
 *
 * @return The converted string in snake case.
 */
fun String.upperCamelToSnakeCase(): String {
    return camelRegex.replace(this) { "_${it.value}" }.lowercase()
}

/**
 * Converts a string from camelCase to snake_case.
 *
 * Example:
 * ```
 * "camelCaseExample".lowerCamelToSnakeCase() // "camel_case_example"
 * ```
 *
 * @return The converted string in snake case.
 */
fun String.lowerCamelToSnakeCase(): String {
    return camelRegex.replace(this) { "_${it.value}" }.lowercase()
}

/**
 * Converts a string from snake_case to UpperCamelCase (PascalCase).
 *
 * Example:
 * ```
 * "example_variable_name".snakeToUpperCamelCase() // "ExampleVariableName"
 * ```
 *
 * @return The converted string in PascalCase.
 */
fun String.snakeToUpperCamelCase(): String {
    return this.split("_")
        .joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
}

/**
 * Converts a string from snake_case to lowerCamelCase.
 *
 * Example:
 * ```
 * "snake_case_example".snakeToLowerCamelCase() // "snakeCaseExample"
 * ```
 *
 * @return The converted string in lower camel case.
 */
fun String.snakeToLowerCamelCase(): String {
    return snakeRegex.replace(this) {
        it.value.replace("_", "").uppercase()
    }
}

/**
 * Converts a string from snake_case to SCREAMING_SNAKE_CASE.
 *
 * Example:
 * ```
 * "example_variable_name".toScreamingSnakeCase() // "EXAMPLE_VARIABLE_NAME"
 * ```
 *
 * @return The converted string in SCREAMING_SNAKE_CASE.
 */
fun String.toScreamingSnakeCase(): String = this.uppercase()

/**
 * Converts a string from SCREAMING_SNAKE_CASE to snake_case.
 *
 * Example:
 * ```
 * "EXAMPLE_VARIABLE_NAME".screamingSnakeToSnakeCase() // "example_variable_name"
 * ```
 *
 * @return The converted string in snake_case.
 */
fun String.screamingSnakeToSnakeCase(): String = this.lowercase()

/**
 * Converts a string from camelCase to kebab-case.
 *
 * Example:
 * ```
 * "camelCaseExample".camelToKebabCase() // "camel-case-example"
 * ```
 *
 * @return The converted string in kebab-case.
 */
fun String.camelToKebabCase(): String {
    return camelRegex.replace(this) { "-${it.value}" }.lowercase()
}

/**
 * Converts a string from kebab-case to lowerCamelCase.
 *
 * Example:
 * ```
 * "kebab-case-example".kebabToLowerCamelCase() // "kebabCaseExample"
 * ```
 *
 * @return The converted string in lower camel case.
 */
fun String.kebabToLowerCamelCase(): String {
    return this.split("-").mapIndexed { index, s ->
        if (index == 0) s.lowercase() else s.replaceFirstChar { it.uppercase() }
    }.joinToString("")
}

/**
 * Converts a string from kebab-case to UpperCamelCase (PascalCase).
 *
 * Example:
 * ```
 * "kebab-case-example".kebabToUpperCamelCase() // "KebabCaseExample"
 * ```
 *
 * @return The converted string in PascalCase.
 */
fun String.kebabToUpperCamelCase(): String {
    return this.split("-")
        .joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
}

/**
 * Converts a string from kebab-case to snake_case.
 *
 * Example:
 * ```
 * "kebab-case-example".kebabToSnakeCase() // "kebab_case_example"
 * ```
 *
 * @return The converted string in snake_case.
 */
fun String.kebabToSnakeCase(): String {
    return this.replace("-", "_")
}

/**
 * Converts a string from snake_case to kebab-case.
 *
 * Example:
 * ```
 * "snake_case_example".snakeToKebabCase() // "snake-case-example"
 * ```
 *
 * @return The converted string in kebab-case.
 */
fun String.snakeToKebabCase(): String {
    return this.replace("_", "-")
}

/**
 * Converts English and Arabic digits in a string to their equivalent Persian digits.
 * It replaces English digits and Arabic digits with Persian digits.
 *
 * Example:
 * ```kotlin
 * val englishString = "123456789"
 * val persianString = englishString.toPersianNumbers()
 * println(persianString) // Output: "۱۲۳۴۵۶۷۸۹"
 * ```
 *
 * @return The string with English and Arabic digits replaced by Persian digits.
 */
fun String?.toPersianNumbers(): String {

    if (this.isNullOrEmpty()) return this.orEmpty()

    return this
        .map { char ->
            DigitMappings.arabicToPersianMap[char] ?: DigitMappings.englishToPersianMap[char] ?: char
        }
        .joinToString("")
        .replace(".", "/")
        .replace("٫", "/")
}

/**
 * Converts Persian and Arabic digits in a string to their equivalent English digits.
 * It replaces Persian digits and Arabic digits with English digits.
 *
 * Example:
 * ```kotlin
 * val persianString = "۱۲۳۴۵۶۷۸۹"
 * val englishString = persianString.toEnglishNumbers()
 * println(englishString) // Output: "123456789"
 * ```
 *
 * @return The string with Persian and Arabic digits replaced by English digits.
 */
fun String?.toEnglishNumbers(): String {

    if (this.isNullOrEmpty()) return this.orEmpty()

    return this
        .map { char ->
            DigitMappings.persianToEnglishMap[char] ?: DigitMappings.arabicToEnglishMap[char] ?: char
        }
        .joinToString("")
        .replace("/", ".")
        .replace("٫", ".")
}

/**
 * Converts Persian and English digits in a string to their equivalent Arabic digits.
 * It replaces Persian digits and English digits with Arabic digits.
 *
 * Example:
 * ```kotlin
 * val englishString = "123456789"
 * val arabicString = englishString.toArabicNumbers()
 * println(arabicString) // Output: "١٢٣٤٥٦٧٨٩"
 * ```
 *
 * @return The string with Persian and English digits replaced by Arabic digits.
 */
fun String?.toArabicNumbers(): String {

    if (this.isNullOrEmpty()) return this.orEmpty()

    return this
        .map { char ->
            DigitMappings.persianToArabicMap[char] ?: DigitMappings.englishToArabicMap[char] ?: char
        }
        .joinToString("")
        .replace("/", "٫")
        .replace(".", "٫")
}