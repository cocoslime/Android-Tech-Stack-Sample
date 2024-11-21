package com.cocoslime.presentation.data.service

import com.cocoslime.data.model.GithubRepoResponse
import com.cocoslime.data.model.Owner
import com.cocoslime.data.service.GithubService

private const val MAX_SIZE = 100

class TestGithubService : GithubService {

    override suspend fun getRepos(
        username: String,
        perPage: Int,
        page: Int
    ): List<GithubRepoResponse> {
        val startIndex = (page - 1) * perPage
        val endIndex = minOf(startIndex + perPage, MAX_SIZE)
        return (startIndex until endIndex).map { index ->
            createFakeGithubRepoResponse(index)
        }
    }

    companion object {
        fun createFakeGithubRepoResponse(
            index: Int
        ): GithubRepoResponse {
            return GithubRepoResponse(
                id = index.toLong(),
                name = "Repo $index",
                url = "",
                description = null,
                language = null,
                owner = Owner(
                    avatarUrl = ""
                )
            )
        }
    }
}