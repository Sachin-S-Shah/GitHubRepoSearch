package extension.domain.githubreposearch.data.network


import androidx.paging.ExperimentalPagingApi
import com.haroldadmin.cnradapter.NetworkResponse
import extension.domain.githubreposearch.data.models.gitHubRepoReadme.ErrorResponse
import extension.domain.githubreposearch.data.models.gitHubRepoReadme.GitHubRepoReadmeResponse
import extension.domain.githubreposearch.data.models.gitHubRepos.GitHubRepoSearchResponse
import extension.domain.githubreposearch.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@ExperimentalPagingApi
interface APIService {

    //https://api.github.com/search/repositories?q=android&page=3&per_page=30
    @GET("search/repositories")
    suspend fun paginated(
        @Query("q") q: String,
        @Query("page") after: Int?,
        @Query("per_page") per_page: Int = Constants.DEFAULT_PAGE_SIZE
    ): GitHubRepoSearchResponse


    //https://api.github.com/repos/facebook/react/readme
    @GET("repos/{owner}/{repo}/readme")
    suspend fun getReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): NetworkResponse<GitHubRepoReadmeResponse, ErrorResponse>

}