package com.cocoslime.presentation.data.service

import com.cocoslime.data.model.GithubRepoResponse
import com.cocoslime.data.model.Owner
import com.cocoslime.data.service.GithubService

class TestGithubService : GithubService {

    val repoResponseList = mutableListOf<GithubRepoResponse>()

    override suspend fun getRepos(
        username: String,
        perPage: Int,
        page: Int
    ): List<GithubRepoResponse> {
        val startIndex = (page - 1) * perPage
        val endIndex = minOf(startIndex + perPage, repoResponseList.size)
        return repoResponseList.subList(startIndex, endIndex)
    }
}