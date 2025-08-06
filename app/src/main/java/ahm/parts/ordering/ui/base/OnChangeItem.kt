package ahm.parts.ordering.ui.base
import android.view.View

/**
 * listener yang digunakan ketika ada aksi click dari sebuah item
 *
 */

interface OnChangeItem {
    fun onClick(position: Int, view: View)
}