package extension.domain.githubreposearch.di

import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import extension.domain.githubreposearch.data.gitHubRepo.GitHubRepoRepo
import extension.domain.githubreposearch.data.network.APIService
import javax.inject.Singleton

@ExperimentalPagingApi
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideGitHubRepoSearchRepo(apiService: APIService): GitHubRepoRepo {
        return GitHubRepoRepo(apiService)
    }
}