package aren.kamalyan.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import aren.kamalyan.data.network.api.GithubApi
import aren.kamalyan.data.repository.paging.GithubRepoPagingSource
import aren.kamalyan.domain.entity.DateFilterUiEntity
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGER_REPO_ITEM_SIZE = 20

class GithubRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi
) : GithubRepository {
    override fun searchRepositories(dateFilter: DateFilterUiEntity): Flow<PagingData<RepoUiEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGER_REPO_ITEM_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GithubRepoPagingSource(
                    githubApi = githubApi,
                    dateFilter = dateFilter,
                )
            }
        ).flow
}