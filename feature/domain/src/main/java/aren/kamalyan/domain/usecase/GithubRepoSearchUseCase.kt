package aren.kamalyan.domain.usecase

import androidx.paging.PagingData
import aren.kamalyan.domain.entity.DateFilterUiEntity
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GithubRepoSearchUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    operator fun invoke(dateFilter: DateFilterUiEntity): Flow<PagingData<RepoUiEntity>> =
        repository.searchRepositories(dateFilter)
}