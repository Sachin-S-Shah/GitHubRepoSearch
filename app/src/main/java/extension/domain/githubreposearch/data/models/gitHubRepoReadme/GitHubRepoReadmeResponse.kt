package extension.domain.githubreposearch.data.models.gitHubRepoReadme


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubRepoReadmeResponse(
    @SerializedName("download_url")
    @Expose
    var download_url: String
) : Parcelable

data class ErrorResponse(val message: String)