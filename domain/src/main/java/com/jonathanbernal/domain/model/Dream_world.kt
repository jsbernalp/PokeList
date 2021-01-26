package com.jonathanbernal.domain.model

import com.squareup.moshi.Json

data class Dream_world(
    @Json(name = "front_default")
    val front_default: String,

    @Json(name = "front_female")
    val front_female: String)
