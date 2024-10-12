package com.ng.tvshowsdb.shared.network.client.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    @SerialName("page")
    val page: Long? = null,
    @SerialName("results")
    val results: T? = null
)