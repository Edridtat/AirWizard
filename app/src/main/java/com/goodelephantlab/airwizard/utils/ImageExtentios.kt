package com.goodelephantlab.airwizard.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.goodelephantlab.airwizard.R

fun ImageView.loadImage(name: String) {
    val url = this.context.getString(R.string.logo_path, name)
    Glide.with(context)
        .load(url)
        .into(this)
}