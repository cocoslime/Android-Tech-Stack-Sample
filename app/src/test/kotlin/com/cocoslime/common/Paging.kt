package com.cocoslime.common

import com.cocoslime.data.model.GithubRepoResponse
import com.cocoslime.data.model.Owner

fun createFakeGithubRepoResponse(
    id: Long
): GithubRepoResponse {
    return GithubRepoResponse(
        id = id,
        name = "Repo $id",
        url = "",
        description = null,
        language = null,
        owner = Owner(
            avatarUrl = ""
        )
    )
}