package com.cocoslime.data.service

import com.cocoslime.data.model.GithubRepoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

//    https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user
    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username", encoded = true) username: String,
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1
    ): List<GithubRepoResponse>
}