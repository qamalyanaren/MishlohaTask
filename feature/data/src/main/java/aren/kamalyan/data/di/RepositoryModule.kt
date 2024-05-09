package aren.kamalyan.data.di


import aren.kamalyan.data.persistent.PrefManagerImpl
import aren.kamalyan.data.repository.FavoriteRepositoryImpl
import aren.kamalyan.data.repository.GithubRepositoryImpl
import aren.kamalyan.domain.persistent.PrefManager
import aren.kamalyan.domain.repository.FavoriteRepository
import aren.kamalyan.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindPrefManager(impl: PrefManagerImpl): PrefManager

    @Binds
    fun bindGithubRepository(impl: GithubRepositoryImpl): GithubRepository

    @Binds
    fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository
}