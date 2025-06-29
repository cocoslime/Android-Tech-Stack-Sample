package com.cocoslime.samples.apps.androidtechstack.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRepoResponse(
    val id: Long,
    val name: String,
    @SerialName("html_url") val url: String,
    val description: String?,
    val language: String?,
    val owner: Owner,
)

@Serializable
data class Owner(
    @SerialName("avatar_url") val avatarUrl: String,
)
