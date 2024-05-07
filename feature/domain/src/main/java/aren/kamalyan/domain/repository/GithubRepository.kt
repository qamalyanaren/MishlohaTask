package aren.kamalyan.domain.repository

import androidx.paging.PagingData
import aren.kamalyan.domain.entity.DateFilterUiEntity
import aren.kamalyan.domain.entity.RepoUiEntity
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun searchRepositories(
        dateFilter: DateFilterUiEntity
    ): Flow<PagingData<RepoUiEntity>>
}