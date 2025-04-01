@file:Suppress("SimpleDateFormat")

package ir.farsroidx.m31

import java.text.DecimalFormat

// TODO: Object ========================================================================= Object ===

typealias StringArray = Array<String>

// Log tag for all log messages
internal const val TAG = "Andromeda"

// Global DecimalFormat instance for formatting numbers with thousands separators
internal val decimalFormat = DecimalFormat("###,###,###")

// Global regular expression to match uppercase letters that are preceded by lowercase letters or digits (for camel case to snake case conversion)
internal val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()

// Global regular expression to match snake_case letters (underscore followed by a letter)
internal val snakeRegex = "_[a-zA-Z]".toRegex()

internal val validPhoneOperators by lazy { listOf("090", "091", "092", "093", "099") }

// Mapping Arabic characters to Persian
internal val arabicCharactersToPersianCharactersMap = mapOf(
    'ا' to 'ا', 'ب' to 'ب', 'پ' to 'پ', 'ت' to 'ت', 'ث' to 'ث',
    'ج' to 'ج', 'چ' to 'چ', 'ح' to 'ح', 'خ' to 'خ', 'د' to 'د',
    'ذ' to 'ذ', 'ر' to 'ر', 'ز' to 'ز', 'ژ' to 'ژ', 'س' to 'س',
    'ش' to 'ش', 'ص' to 'ص', 'ض' to 'ض', 'ط' to 'ط', 'ظ' to 'ظ',
    'ع' to 'ع', 'غ' to 'غ', 'ف' to 'ف', 'ق' to 'ق', 'ك' to 'ک',
    'گ' to 'گ', 'ل' to 'ل', 'م' to 'م', 'ن' to 'ن', 'ه' to 'ه',
    'و' to 'و', 'ى' to 'ی', 'ي' to 'ی', 'ئ' to 'ی', 'آ' to 'آ',
    'ة' to 'ه'
)

/**
 * Holds digit mappings for Persian, Arabic, and English numerals.
 */
internal object DigitMappings {

    val persianDigits = "۰۱۲۳۴۵۶۷۸۹".toCharArray()
    val arabicDigits  = "٠١٢٣٤٥٦٧٨٩".toCharArray()
    val englishDigits = "0123456789".toCharArray()

    val persianToEnglishMap = persianDigits.zip(englishDigits).toMap()
    val arabicToEnglishMap  = arabicDigits.zip(englishDigits).toMap()
    val englishToPersianMap = englishDigits.zip(persianDigits).toMap()
    val englishToArabicMap  = englishDigits.zip(arabicDigits).toMap()
    val arabicToPersianMap  = arabicDigits.zip(persianDigits).toMap()
    val persianToArabicMap  = persianDigits.zip(arabicDigits).toMap()

}