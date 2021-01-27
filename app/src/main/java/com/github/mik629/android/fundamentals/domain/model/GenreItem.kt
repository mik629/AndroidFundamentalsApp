package com.github.mik629.android.fundamentals.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreItem(val id: Int, val name: String) : Parcelable