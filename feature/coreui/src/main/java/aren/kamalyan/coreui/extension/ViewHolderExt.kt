package aren.kamalyan.coreui.extension

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder

fun ViewHolder.getString(@StringRes resId: Int, vararg args: Any): String =
    itemView.context.getString(resId, *args)

fun ViewHolder.getDrawable(@DrawableRes resId: Int) =
    ContextCompat.getDrawable(itemView.context, resId)

fun ViewHolder.getColor(@ColorRes resId: Int) =
    ContextCompat.getColor(itemView.context, resId)

fun ViewHolder.getFont(@FontRes resId: Int) =
    itemView.context.getFont(resId)