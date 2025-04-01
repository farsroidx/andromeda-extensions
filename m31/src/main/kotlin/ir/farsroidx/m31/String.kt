@file:Suppress("unused")

package ir.farsroidx.m31

// TODO: String ========================================================================= String ===

/**
 * Calculates the Levenshtein distance between two strings.
 *
 * The Levenshtein distance is the minimum number of single-character edits (insertions, deletions, or substitutions) required to change one string into the other.
 *
 * Example:
 * ```kotlin
 * val distance = "hello".getLevenshteinDistance("hallo")
 * println(distance) // Output: 1 (Only one substitution)
 * ```
 *
 * @param other The other string to compare to.
 * @return The Levenshtein distance between the two strings.
 */
fun String.getLevenshteinDistance(other: String): Int {

    val len1 = this.length
    val len2 = other.length
    val dp   = Array(len1 + 1) { IntArray(len2 + 1) }

    // Initialize the base cases (transforming empty string to another)
    for (i in 0..len1) dp[i][0] = i
    for (j in 0..len2) dp[0][j] = j

    // Fill the dp array with the number of operations needed to transform substrings
    for (i in 1..len1) {
        for (j in 1..len2) {
            val cost = if (this[i - 1] == other[j - 1]) 0 else 1
            dp[i][j] = minOf(
                dp[i - 1][j] + 1,       // Deletion
                dp[i][j - 1] + 1,       // Insertion
                dp[i - 1][j - 1] + cost // Substitution
            )
        }
    }

    return dp[len1][len2]
}

/**
 * Calculates the similarity percentage between two strings based on the Levenshtein distance.
 *
 * The similarity is calculated as:
 *
 *   (maxLength - LevenshteinDistance) / maxLength * 100
 *
 * Where:
 * - `maxLength` is the maximum length of the two strings.
 * - `LevenshteinDistance` is the number of single-character edits required to change one string into the other.
 *
 * Example:
 * ```kotlin
 * val similarity = "hello".similarityWith("hallo")
 * println(similarity) // Output: 80.0
 * ```
 *
 * @param other The other string to compare to.
 * @param digits The number of decimal places to round the similarity percentage to (default is 3).
 * @return The similarity percentage between the two strings as a Double value.
 * @throws IllegalArgumentException if either of the strings is null.
 */
fun String?.similarityWith(other: String?, digits: Int = 3): Double {

    if (this == null && other == null)
        return 100.0
    else if (this == null)
        return 0.0
    else if (other == null)
        return 0.0

    val maxLength = kotlin.math.max(this.length, other.length)

    val result = if (maxLength > 0) {
        (maxLength * 1.0 - this.getLevenshteinDistance(other)) / maxLength * 1.0
    } else 1.0

    return String.format("%.${digits}f", result * 100).toDouble()
}

/**
 * Converts Arabic characters to their Persian equivalents.
 *
 * This function replaces Arabic characters (like 'ي', 'ك') with their corresponding Persian characters.
 * The mapping follows the standard Persian-Arabic character set.
 *
 * Example:
 * ```kotlin
 * val arabicText = "سلام"
 * val persianText = arabicText.arabicToPersian()
 * println(persianText) // Output: "سلام"
 * ```
 *
 * @return A new string where Arabic characters are replaced with Persian characters.
 */
fun String.arabicToPersian(): String {

    // Building the resulting string
    val result = StringBuilder()

    // Iterate through each character and apply the mapping
    for (char in this) {
        val persianChar = arabicCharactersToPersianCharactersMap[char]
        result.append(persianChar ?: char)
    }

    return result.toString()
}

/**
 * Returns the string if it's not null or empty, otherwise returns null.
 *
 * This method is useful for safely handling nullable strings, avoiding direct
 * null or empty checks and providing a more elegant fallback of `null`.
 *
 * Example usage:
 * ```kotlin
 * val text: String? = "Hello"
 * val result = text.getOrNull() // result is "Hello"
 *
 * val emptyText: String? = ""
 * val emptyResult = emptyText.getOrNull() // emptyResult is null
 * ```
 *
 * @return The string itself if it's not null or empty, otherwise null.
 */
fun String?.getOrNull(): String? = if (this?.trim().isNullOrEmpty()) null else this