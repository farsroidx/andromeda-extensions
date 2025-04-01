@file:Suppress("unused")

package ir.farsroidx.m31

import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat

/**
 * Sets the visibility of the view to visible (View.VISIBLE).
 */
fun View.visible() { this.visibility = View.VISIBLE }

/**
 * Sets the visibility of the view to invisible (View.INVISIBLE).
 */
fun View.invisible() { this.visibility = View.INVISIBLE }

/**
 * Sets the visibility of the view to gone (View.GONE).
 */
fun View.gone() { this.visibility = View.GONE }

/**
 * Sets the visibility of the view to either visible or invisible based on the boolean flag `isVisible`.
 *
 * @param isVisible If true, the view becomes visible. If false, the view becomes invisible.
 */
fun View.visibleOrInvisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

/**
 * Sets the visibility of the view to either visible or gone based on the boolean flag `isVisible`.
 *
 * @param isVisible If true, the view becomes visible. If false, the view becomes gone.
 */
fun View.visibleOrGone(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

/**
 * Sets the visibility of the view to either invisible or visible based on the boolean flag `isInvisible`.
 *
 * @param isInvisible If true, the view becomes invisible. If false, the view becomes visible.
 */
fun View.invisibleOrVisible(isInvisible: Boolean) {
    this.visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
}

/**
 * Sets the visibility of the view to either invisible or gone based on the boolean flag `isInvisible`.
 *
 * @param isInvisible If true, the view becomes invisible. If false, the view becomes gone.
 */
fun View.invisibleOrGone(isInvisible: Boolean) {
    this.visibility = if (isInvisible) View.INVISIBLE else View.GONE
}

/**
 * Sets the visibility of the view to either gone or visible based on the boolean flag `isGone`.
 *
 * @param isGone If true, the view becomes gone. If false, the view becomes visible.
 */
fun View.goneOrVisible(isGone: Boolean) {
    this.visibility = if (isGone) View.GONE else View.VISIBLE
}

/**
 * Sets the visibility of the view to either gone or invisible based on the boolean flag `isGone`.
 *
 * @param isGone If true, the view becomes gone. If false, the view becomes invisible.
 */
fun View.goneOrInvisible(isGone: Boolean) {
    this.visibility = if (isGone) View.GONE else View.INVISIBLE
}

/**
 * Enables the view, making it interactable.
 */
fun View.enable() = enableOrDisable(true)

/**
 * Disables the view, making it non-interactable.
 */
fun View.disable() = disableOrEnable(true)

/**
 * Enables or disables the view based on the boolean flag `isEnable`.
 *
 * @param isEnable If true, the view is enabled. If false, the view is disabled.
 */
fun View.enableOrDisable(isEnable: Boolean) {
    this.isEnabled = isEnable
}

/**
 * Disables or enables the view based on the boolean flag `isDisable`.
 *
 * @param isDisable If true, the view is disabled. If false, the view is enabled.
 */
fun View.disableOrEnable(isDisable: Boolean) {
    this.isEnabled = !isDisable
}

/**
 * Applies a custom indicator view to the `LinearLayout`.
 * It adds indicator views (dots) for each page and allows customization for selected and unselected states.
 *
 * @param size The total number of indicator dots to be displayed.
 * @param position The index of the currently selected dot.
 * @param onSelectedBuilder A lambda function that defines the layout parameters for the selected indicator.
 *                  This will be called for the currently selected indicator, and the `index` parameter refers to the index of the selected dot.
 * @param onUnSelectedBuilder A lambda function that defines the layout parameters for the unselected indicators.
 *                  This will be called for each unselected indicator, and the `index` parameter refers to the index of each unselected dot in the loop.
 */
fun LinearLayout.applyAsIndicator(
    size: Int = 0,
    position: Int = 0,
    onSelectedBuilder: ViewGroup.(index: Int) -> ViewGroup.LayoutParams,
    onUnSelectedBuilder: ViewGroup.(index: Int) -> ViewGroup.LayoutParams,
) = this.asIndicator(size, position, onSelectedBuilder, onUnSelectedBuilder)

/**
 * Applies a custom indicator view to the `LinearLayoutCompat`.
 * It adds indicator views (dots) for each page and allows customization for selected and unselected states.
 *
 * @param size The total number of indicator dots to be displayed.
 * @param position The index of the currently selected dot.
 * @param onSelectedBuilder A lambda function that defines the layout parameters for the selected indicator.
 *                  This will be called for the currently selected indicator, and the `index` parameter refers to the index of the selected dot.
 * @param onUnSelectedBuilder A lambda function that defines the layout parameters for the unselected indicators.
 *                  This will be called for each unselected indicator, and the `index` parameter refers to the index of each unselected dot in the loop.
 */
fun LinearLayoutCompat.applyAsIndicator(
    size: Int = 0,
    position: Int = 0,
    onSelectedBuilder: ViewGroup.(index: Int) -> ViewGroup.LayoutParams,
    onUnSelectedBuilder: ViewGroup.(index: Int) -> ViewGroup.LayoutParams,
) = this.asIndicator(size, position, onSelectedBuilder, onUnSelectedBuilder)

private fun ViewGroup.asIndicator(
    size: Int,
    position: Int,
    onSelectedBuilder: ViewGroup.(index: Int) -> ViewGroup.LayoutParams,
    onUnSelectedBuilder: ViewGroup.(index: Int) -> ViewGroup.LayoutParams,
) {

    this.removeAllViews()

    if (size > 0) {

        for (index in 0 until size) {

            val view = View(context).apply {
                layoutParams = if (index == position) onSelectedBuilder(index) else onUnSelectedBuilder(index)
            }

            this.addView(view)
        }
    }

    this.visibleOrGone(size > 1)
}

/**
 * Removes the click listener from the view by setting it to null.
 */
fun View.removeClickListener() = this.setOnClickListener(null)

/**
 * Removes the long click listener from the view by setting it to null.
 */
fun View.removeLongClickListener() = this.setOnLongClickListener(null)

/**
 * Loads a local HTML file from the assets folder into a WebView.
 *
 * @param filePath The path of the HTML file within the 'assets' folder.
 *                 It should be a relative path starting from the 'assets' folder.
 */
fun WebView.loadFromAssets(filePath: String) =
    this.loadUrl("file:///android_asset/$filePath")

/**
 * Retrieves the value of the item at the specified index in the Spinner.
 *
 * @param index The position of the item in the Spinner's list.
 * @return The text value of the item at the specified index. If the index is invalid (less than 0),
 *         an empty string is returned.
 *
 * This function retrieves the child view of the Spinner at the given index, casts it to a
 * `TextView`, and then returns its text value. If the index is invalid (less than 0), an
 * empty string is returned to prevent errors.
 */
fun Spinner.getValueFrom(index: Int): String {
    return if (index < 0) "" else (this.getChildAt(index) as TextView).text.toString()
}
