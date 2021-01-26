package com.jonathanbernal.domain.model

import com.squareup.moshi.Json


data class Sprites(
    @Json(name = "back_default")
    val back_default: String,
    @Json(name = "other")
    val other: Other)
