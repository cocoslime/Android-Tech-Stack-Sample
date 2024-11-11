package com.cocoslime.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRepoResponse(
    val id: Long,
    val name: String,
    @SerialName("html_url") val url: String,
    val description: String,
)
