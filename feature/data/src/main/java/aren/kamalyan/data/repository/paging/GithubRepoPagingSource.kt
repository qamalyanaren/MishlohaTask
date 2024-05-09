package aren.kamalyan.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import aren.kamalyan.data.db.FavoriteDao
import aren.kamalyan.data.mapper.MapperRepoEntityToUiEntity
import aren.kamalyan.data.network.api.GithubApi
import aren.kamalyan.domain.entity.DateFilterUiEntity
import aren.kamalyan.domain.entity.RepoUiEntity


class GithubRepoPagingSource(
    private val githubApi: GithubApi,
    private val dateFilter: DateFilterUiEntity,
    private val favoriteDao: FavoriteDao,
) :
    PagingSource<String, RepoUiEntity>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RepoUiEntity> {
        val url = params.key
        return try {
            val response =
                if (url != null) githubApi.searchRepositoriesByUrl(url) else githubApi.searchRepositories(
                    dateFilter.toDateQuery()
                )
            if (response.isSuccessful) {
                val repoList = response.body()?.items ?: emptyList()
                val links = parseLinkHeader(response.headers()["Link"])
                val favorites = favoriteDao.findAll()
                return LoadResult.Page(
                    data = MapperRepoEntityToUiEntity(favorites).map(repoList),
                    prevKey = links["prev"],
                    nextKey = links["next"]
                )
            } else {
                return LoadResult.Error(Exception("Failed to load data!"))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    private fun parseLinkHeader(linkHeader: String?): Map<String, String?> {
        linkHeader ?: return emptyMap()
        return linkHeader
            .split(", ")
            .mapNotNull { link ->
                val matchResult = Regex("""<([^>]+)>; rel="(\w+)"""").find(link)
                matchResult?.let { it.groupValues[2] to it.groupValues[1] }
            }.toMap()
    }

    override fun getRefreshKey(state: PagingState<String, RepoUiEntity>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
