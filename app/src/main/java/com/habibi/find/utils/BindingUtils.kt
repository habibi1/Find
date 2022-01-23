package com.habibi.find.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun setImage(imageView: ImageView, source: String?) =
    Glide.with(imageView)
        .load(source)
        .centerCrop()
        .into(imageView)