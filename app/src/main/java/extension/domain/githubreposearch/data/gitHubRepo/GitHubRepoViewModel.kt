package extension.domain.githubreposearch.data.gitHubRepo


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import extension.domain.githubreposearch.data.models.gitHubRepoReadme.GitHubRepoReadmeResponse
import extension.domain.githubreposearch.data.models.gitHubRepos.GitHubRepoSearchItem
import io.reactivex.Observable
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class GitHubRepoViewModel @Inject constructor(
    private val repository: GitHubRepoRepo
) : ViewModel() {

    fun getDataFromCloudPaginated(query: String): Observable<PagingData<GitHubRepoSearchItem>> {
        return repository.getDataFromCloudPaginated(query).cachedIn(viewModelScope)
            .distinctUntilChanged()
    }

    suspend fun getReadme(owner: String, repo: String): GitHubRepoReadmeResponse? {
        return repository.getReadme(owner, repo)
    }
}