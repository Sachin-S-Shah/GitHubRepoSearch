package extension.domain.githubreposearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import extension.domain.githubreposearch.R
import extension.domain.githubreposearch.data.models.gitHubRepos.GitHubRepoSearchItem
import extension.domain.githubreposearch.databinding.RepoItemBinding

class GitHubRepoPaginatedAdapter(val onItemSelected: ((GitHubRepoSearchItem) -> Unit)? = null) :
    PagingDataAdapter<GitHubRepoSearchItem, GitHubRepoPaginatedAdapter.ViewHolder>(
        COMPARATOR
    ) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GitHubRepoSearchItem>() {
            override fun areItemsTheSame(
                oldItem: GitHubRepoSearchItem,
                newItem: GitHubRepoSearchItem
            ) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GitHubRepoSearchItem,
                newItem: GitHubRepoSearchItem
            ) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RepoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(private val itemBinding: RepoItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBindView(data: GitHubRepoSearchItem, position: Int) {
            data.let {
                itemBinding.repoName.text = String.format(
                    itemView.context.getString(R.string.repo_branch),
                    it.name,
                    it.default_branch
                )
                itemBinding.repoDescription.text = it.description
                itemBinding.repoItemMainLayout.setOnClickListener { _ ->
                    onItemSelected?.invoke(it)
                }
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBindView(it, position)
        }
    }
}