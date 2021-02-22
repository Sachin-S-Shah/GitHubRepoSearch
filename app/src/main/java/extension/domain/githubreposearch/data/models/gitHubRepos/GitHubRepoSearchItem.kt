package extension.domain.githubreposearch.data.models.gitHubRepos


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubRepoSearchItem(
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("node_id")
    @Expose
    var node_id: String,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("full_name")
    @Expose
    var full_name: String,

    @SerializedName("owner")
    @Expose
    var owner: GitHubRepoSearchItemOwner,

    @SerializedName("description")
    @Expose
    var description: String,

    @SerializedName("html_url")
    @Expose
    var html_url: String,

    @SerializedName("forks")
    @Expose
    var forks: Int? = 0,

    @SerializedName("open_issues")
    @Expose
    var open_issues: Int? = 0,

    @SerializedName("default_branch")
    @Expose
    var default_branch: String
) : Parcelable


@Parcelize
data class GitHubRepoSearchItemOwner(
    @SerializedName("login")
    @Expose
    var login: String
) : Parcelable