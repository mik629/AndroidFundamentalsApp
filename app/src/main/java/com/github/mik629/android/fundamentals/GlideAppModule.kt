package com.github.mik629.android.fundamentals

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideAppModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
