package extension.domain.githubreposearch.data.gitHubRepo


import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import extension.domain.githubreposearch.App
import extension.domain.githubreposearch.data.models.gitHubRepos.GitHubRepoSearchItem
import extension.domain.githubreposearch.data.network.APIService
import extension.domain.githubreposearch.ui.main.MainActivity
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class GitHubRepoSearchDataPagingSource(val query: String, val apiService: APIService) :
    PagingSource<Int, GitHubRepoSearchItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubRepoSearchItem> {
        val page = params.key
        return try {
            val response = apiService.paginated(q = query, after = page)
            val data = response.items
            LoadResult.Page(data, prevKey = page, nextKey = ((page ?: 0) + 1))
        } catch (exception: IOException) {
            (App.getCurrentActivity as? MainActivity)?.showErrorMessage()
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            (App.getCurrentActivity as? MainActivity)?.showErrorMessage()
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GitHubRepoSearchItem>): Int? {
        return state.anchorPosition
    }
}