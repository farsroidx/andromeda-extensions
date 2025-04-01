@file:Suppress("unused")

package ir.farsroidx.m31

import android.util.Patterns
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.collections.any
import kotlin.collections.mapIndexed
import kotlin.collections.sum
import kotlin.text.startsWith

// TODO: Validation ================================================================= Validation ===

/**
 * Validates if the string is a valid email.
 *
 * @return true if the string is a valid email, false otherwise.
 */
fun String?.isValidEmail(): Boolean {
    return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Validates if the string is a valid phone number.
 *
 * @return true if the string is a valid phone number, false otherwise.
 */
fun String?.isValidPhoneNumber(): Boolean {
    return !isNullOrEmpty() && Patterns.PHONE.matcher(this).matches()
}

/**
 * Validates if the string is a valid password.
 * Password should have at least a minimum length and at most a maximum length,
 * contain at least one digit and one letter.
 *
 * @param minLength the minimum length of the password.
 * @param maxLength the maximum length of the password.
 * @return true if the string is a valid password, false otherwise.
 */
fun String?.isValidPassword(minLength: Int = 6, maxLength: Int = 20): Boolean {
    return !isNullOrEmpty() &&
        this.length in minLength..maxLength &&
        this.matches(".*[0-9].*".toRegex()) &&
        this.matches(".*[a-zA-Z].*".toRegex())
}

/**
 * Validates if the string is a valid number.
 *
 * @return true if the string is a valid number, false otherwise.
 */
fun String?.isValidNumber(): Boolean {
    return !isNullOrEmpty() && this.matches("-?\\d+(\\.\\d+)?".toRegex()) // Matches integers and decimals
}

/**
 * Validates if the string is not null or empty.
 *
 * @return true if the string is not null or empty, false otherwise.
 */
fun String?.isNotNullOrEmpty(): Boolean {
    return !isNullOrEmpty()
}

/**
 * Validates if the string is a valid date.
 * Date format should be "yyyy-MM-dd".
 *
 * @return true if the string is a valid date, false otherwise.
 */
fun String?.isValidDate(format: String = "yyyy-MM-dd"): Boolean {
    return !isNullOrEmpty() && try {
        SimpleDateFormat(format, Locale.getDefault()).parse(this)
        true
    } catch (e: ParseException) {
        false
    }
}

/**
 * Validates if the string does not have leading or trailing spaces.
 *
 * @return true if the string does not have leading or trailing spaces, false otherwise.
 */
fun String?.hasNoSpaces(): Boolean {
    return this?.trim() == this
}

/**
 * Validates if the string represents a valid positive integer.
 *
 * @return true if the string represents a valid positive integer, false otherwise.
 */
fun String?.isPositiveInteger(): Boolean {
    return !isNullOrEmpty() && this.matches("\\d+".toRegex()) && this.toInt() > 0
}

/**
 * Validates if the string is a valid number within a specified range.
 *
 * @param min the minimum value (inclusive).
 * @param max the maximum value (inclusive).
 * @return true if the string is a valid number within the specified range, false otherwise.
 */
fun String?.isValidNumberInRange(min: Int, max: Int): Boolean {
    return !isNullOrEmpty() && this.toIntOrNull()?.let { it in min..max } == true
}

/**
 * Validates if the string contains at least one specific character.
 *
 * @param char the character to check for.
 * @return true if the string contains the specific character, false otherwise.
 */
fun String?.containsChar(char: Char): Boolean {
    return !isNullOrEmpty() && this.contains(char)
}

/**
 * Validates if the string is a valid credit card number using the Luhn algorithm.
 *
 * @return true if the string is a valid credit card number, false otherwise.
 */
fun String?.isValidCreditCard(): Boolean {
    return !isNullOrEmpty() && this.replace(" ", "").matches("\\d+".toRegex()) && luhnCheck(this)
}

private fun luhnCheck(cardNumber: String): Boolean {
    var sum = 0
    var shouldDouble = false
    for (i in cardNumber.length - 1 downTo 0) {
        var digit = cardNumber[i].digitToInt()
        if (shouldDouble) {
            digit *= 2
            if (digit > 9) digit -= 9
        }
        sum += digit
        shouldDouble = !shouldDouble
    }
    return sum % 10 == 0
}

/**
 * Validates an Iranian national code (کد ملی ایران).
 *
 * The code must be exactly 10 digits and pass the checksum validation algorithm.
 *
 * @return `true` if the national code is valid, otherwise `false`.
 */
fun String.isValidIranianNationalCode(): Boolean {

    if (this.length != 10 || !this.all { it.isDigit() }) return false

    val digits = this.map { it.digitToInt() }
    val check = digits[9]
    val sum = digits.take(9).mapIndexed { i, x -> x * (10 - i) }.sum() % 11

    return check == if (sum < 2) sum else 11 - sum
}

/**
 * Validates an Iranian mobile number.
 *
 * The number must be 11 digits long, start with "09", and belong to a valid operator prefix.
 *
 * @return `true` if the mobile number is valid, otherwise `false`.
 */
fun String.isValidIranianMobileNumber(): Boolean {
    return length == 11 && startsWith("09") && validPhoneOperators.any { startsWith(it) }
}