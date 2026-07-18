package com.healthtracker.util

import android.content.Context
import com.healthtracker.R

object FoodImageMapper {
    fun getImageRes(context: Context, imageResName: String?): Int {
        if (imageResName.isNullOrBlank()) return R.drawable.food_placeholder

        val resId = context.resources.getIdentifier(
            imageResName, "drawable", context.packageName
        )
        return if (resId != 0) resId else R.drawable.food_placeholder
    }
}