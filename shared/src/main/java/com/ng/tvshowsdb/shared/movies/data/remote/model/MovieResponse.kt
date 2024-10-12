package com.ng.tvshowsdb.shared.movies.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MovieResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
)