package com.github.mik629.android.fundamentals.ui.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.RatingBarBinding
import com.github.mik629.android.fundamentals.databinding.RatingBarItemBinding
import com.github.mik629.android.fundamentals.databinding.RatingBarSmallBinding

@Suppress("NOTHING_TO_INLINE")
inline fun RatingBarBinding.setRating(context: Context, value: Float) {
    val grey = ContextCompat.getColor(context, R.color.grey)
    with(this) {
        starFive.setGreyIf(grey, value, 5)
        starFour.setGreyIf(grey, value, 4)
        starThree.setGreyIf(grey, value, 3)
        starTwo.setGreyIf(grey, value, 2)
        starOne.setGreyIf(grey, value, 1)
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun RatingBarSmallBinding.setRating(context: Context, value: Float) {
    val grey = ContextCompat.getColor(context, R.color.grey)
    with(this) {
        starFive.setGreyIf(grey, value, 5)
        starFour.setGreyIf(grey, value, 4)
        starThree.setGreyIf(grey, value, 3)
        starTwo.setGreyIf(grey, value, 2)
        starOne.setGreyIf(grey, value, 1)
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
