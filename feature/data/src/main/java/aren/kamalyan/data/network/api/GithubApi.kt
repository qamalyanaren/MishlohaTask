package aren.kamalyan.data.network.api

import aren.kamalyan.data.entity.RepoSearchEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubApi {

    //NOTE: first time used this api, after that searchRepositoriesByUrl
    @GET("search/repositories?sort=stars&order=desc")
    //TODO: read from the local properties
    @Headers("Authorization: Bearer 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")
    suspend fun searchRepositories(
        @Query("q") query: String, //NOTE: date
    ): Response<RepoSearchEntity>

    //NOTE: we get the next url after first call
    @GET
    //TODO: read from the local properties
    @Headers("Authorization: Bearer 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")
    suspend fun searchRepositoriesByUrl(@Url url: String): Response<RepoSearchEntity>
}