@file:Suppress("unused")

package ir.farsroidx.m31

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

// TODO: Notifier ===================================================================== Notifier ===

/**
 * Displays a short toast message in the given `Context`.
 *
 * @param message The text to display in the toast.
 */
fun Context.toast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Displays a short toast message in a `Fragment`.
 *
 * This function ensures that the toast is shown using the context of the fragment.
 *
 * @param message The text to display in the toast.
 */
fun Fragment.toast(message: CharSequence) =
    requireContext().toast(message)

// -------------------------------------------------------------------------------------------------

/**
 * Displays a long toast message in the given `Context`.
 *
 * @param message The text to display in the toast.
 */
fun Context.toastLong(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * Displays a long toast message in a `Fragment`.
 *
 * This function ensures that the toast is shown using the context of the fragment.
 *
 * @param message The text to display in the toast.
 */
fun Fragment.toastLong(message: CharSequence) =
    requireContext().toastLong(message)