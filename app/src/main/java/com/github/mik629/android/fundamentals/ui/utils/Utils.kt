package com.github.mik629.android.fundamentals.ui.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.RatingBarBinding
import com.github.mik629.android.fundamentals.databinding.RatingBarItemBinding

@Suppress("NOTHING_TO_INLINE")
inline fun RatingBarBinding.setRating(context: Context, value: Float) {
    val grey = ContextCompat.getColor(context, R.color.grey)
    with(this) {
        arrayOf(starOne, starTwo, starThree, starFour, starFive)
            .forEachIndexed { index, star ->
                star.setGreyIf(grey, value, index)
            }
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun RatingBarItemBinding.setGreyIf(
    color: Int,
    rating: Float,
    starIndex: Int
) {
    if (rating < starIndex) {
        image.setColorFilter(color)
    }
}
