package aren.kamalyan.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface GithubApi {

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars", //TODO: other options
        @Query("order") order: String = "desc" //TODO: other options
    )

    //NOTE: we get the next url after first call
    @GET
    fun searchRepositoriesByUrl(@Url url: String)
}