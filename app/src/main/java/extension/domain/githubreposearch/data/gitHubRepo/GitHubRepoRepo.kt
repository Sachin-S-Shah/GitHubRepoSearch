package extension.domain.githubreposearch.data.gitHubRepo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.haroldadmin.cnradapter.NetworkResponse
import extension.domain.githubreposearch.data.models.gitHubRepoReadme.GitHubRepoReadmeResponse
import extension.domain.githubreposearch.data.models.gitHubRepos.GitHubRepoSearchItem
import extension.domain.githubreposearch.data.network.APIService
import extension.domain.githubreposearch.utils.Constants
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalPagingApi
class GitHubRepoRepo @Inject constructor(
    private val apiService: APIService
) {
    @ExperimentalCoroutinesApi
    fun getDataFromCloudPaginated(query: String): Observable<PagingData<GitHubRepoSearchItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { GitHubRepoSearchDataPagingSource(query, apiService) }
        ).observable
    }

    suspend fun getReadme(owner: String, repo: String): GitHubRepoReadmeResponse? {
        return when (val response = apiService.getReadme(owner, repo)) {
            is NetworkResponse.Success -> {
                response.body
            }
            is NetworkResponse.ServerError -> {
                null
            }
            is NetworkResponse.NetworkError -> {
                null
            }
            is NetworkResponse.UnknownError -> {
                null
            }
        }
    }
}