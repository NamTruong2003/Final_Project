package com.healthtracker.util

import android.content.Context
import com.healthtracker.R

object ActivityIconMapper {
    fun getIconRes(context: Context, iconName: String?): Int {
        if (iconName.isNullOrBlank()) return R.drawable.activity_placeholder
        val resId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
        return if (resId != 0) resId else R.drawable.activity_placeholder
    }
}