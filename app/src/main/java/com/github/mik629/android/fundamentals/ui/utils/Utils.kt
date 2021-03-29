package com.github.mik629.android.fundamentals.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mik629.android.fundamentals.GlideApp
import com.github.mik629.android.fundamentals.GlideRequest
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.RatingBarBinding

@Suppress("NOTHING_TO_INLINE")
inline fun RatingBarBinding.setRating(context: Context, value: Float) {
    @ColorInt val grey = ContextCompat.getColor(context, R.color.grey)
    @ColorInt val pink = ContextCompat.getColor(context, R.color.pink)
    arrayOf(starOne, starTwo, starThree, starFour, starFive)
        .forEachIndexed { index, star ->
            if (value < index) {
                star.setColorFilter(grey)
            } else {
                star.setColorFilter(pink)
            }
        }
}

@Suppress("NOTHING_TO_INLINE")
inline fun buildGlideRequest(fragment: Fragment): GlideRequest<Drawable> =
    GlideApp.with(fragment)
        .asDrawable()
        .thumbnail(0.1f)
        .placeholder(R.drawable.ic_image_loading)
        .fallback(R.drawable.ic_broken_image)
        .transition(DrawableTransitionOptions.withCrossFade())
        .fitCenter()

// fixme add extension
// LazyThreadSafetyMode.NONE -> UnsafeLazyImpl(initializer)
