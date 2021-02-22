package extension.domain.githubreposearch.data.models.gitHubRepos


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubRepoSearchResponse(
    @SerializedName("total_count")
    @Expose
    var total_count: Int,

    @SerializedName("incomplete_results")
    @Expose
    var incomplete_results: Boolean,

    @SerializedName("items")
    @Expose
    var items: ArrayList<GitHubRepoSearchItem> = ArrayList(),
) : Parcelable