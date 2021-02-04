package com.github.mik629.android.fundamentals.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize // fixme remove after switching on id
data class Actor(
    val id: Int,
    val name: String,
    val photoUrl: String?
) : Parcelable
