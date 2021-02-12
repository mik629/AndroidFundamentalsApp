package com.github.mik629.android.fundamentals.ui.utils

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.RatingBarBinding

@Suppress("NOTHING_TO_INLINE")
inline fun RatingBarBinding.setRating(context: Context, value: Float) {
    @ColorInt val grey = ContextCompat.getColor(context, R.color.grey)
    arrayOf(starOne, starTwo, starThree, starFour, starFive)
        .forEachIndexed { index, star ->
            if (value < index) {
                star.setColorFilter(grey)
            }
        }
}
