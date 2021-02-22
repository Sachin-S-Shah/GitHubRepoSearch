package extension.domain.githubreposearch.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import extension.domain.githubreposearch.R
import extension.domain.githubreposearch.data.gitHubRepo.GitHubRepoViewModel
import extension.domain.githubreposearch.data.models.gitHubRepos.GitHubRepoSearchItem
import extension.domain.githubreposearch.databinding.ActivityMainBinding
import extension.domain.githubreposearch.ui.detail.DetailActivity
import extension.domain.githubreposearch.utils.extensions.showSnackbar
import extension.domain.githubreposearch.utils.views.DividerItemDecorator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: GitHubRepoViewModel by viewModels()

    private val paginatedAdapter by lazy {
        GitHubRepoPaginatedAdapter(onClick)
    }
    val onClick: (item: GitHubRepoSearchItem) -> Unit = {
        startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply {
            putExtra(DetailActivity.REPO, it)
        })
    }

    var getDataCoroutine: Job? = null

    override fun onDestroy() {
        super.onDestroy()
        getDataCoroutine?.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupSearchBox()
        setupRecyclerView()
    }


    private fun setupSearchBox() {
        binding.searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                fetchFromNetwork(Uri.encode(query))
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.trim().isNullOrEmpty()) {
                    fetchFromNetwork(null)
                    //clear recycler view adapter items when search query is cleared
                }
                return false
            }
        })
    }

    private fun setupRecyclerView() {
        binding.reposRecyclerView.apply {
            addItemDecoration(
                DividerItemDecorator(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.divider
                    )!!
                )
            )
            adapter = paginatedAdapter
        }
    }

    @SuppressLint("CheckResult")
    private fun fetchFromNetwork(query: String?) {
        getDataCoroutine?.cancel()
        if (query.isNullOrEmpty()) {
            paginatedAdapter.submitData(lifecycle, PagingData.empty())
        } else {
            getDataCoroutine = CoroutineScope(Dispatchers.Main).launch {
                viewModel.getDataFromCloudPaginated(query).subscribe {
                    lifecycleScope.launch {
                        paginatedAdapter.submitData(it)
                    }
                }
            }
        }
    }

    fun showErrorMessage() {
        showSnackbar(getString(R.string.error))
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }
}