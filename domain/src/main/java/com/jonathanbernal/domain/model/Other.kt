package com.jonathanbernal.domain.model

import com.squareup.moshi.Json


data class Other (
    @Json(name = "dream_world")
    val dream_world: Dream_world )
