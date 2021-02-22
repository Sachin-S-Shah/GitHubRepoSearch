package extension.domain.githubreposearch.ui.detail

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import extension.domain.githubreposearch.R
import extension.domain.githubreposearch.data.gitHubRepo.GitHubRepoViewModel
import extension.domain.githubreposearch.data.models.gitHubRepos.GitHubRepoSearchItem
import extension.domain.githubreposearch.databinding.ActivityDetailBinding
import extension.domain.githubreposearch.utils.extensions.showSnackbar
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object {
        val REPO = "REPO"
    }

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: GitHubRepoViewModel by viewModels()


    lateinit var repo: GitHubRepoSearchItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        repo = intent?.getParcelableExtra(REPO)!!

        title = repo.full_name

        binding.openIssues.text =
            String.format(getString(R.string.xx_open_issues), repo.open_issues)
        binding.forks.text = String.format(getString(R.string.xx_forks), repo.forks)

        lifecycleScope.launch {
            val readmeResponse = viewModel.getReadme(repo.owner.login, repo.name)
            if (readmeResponse?.download_url.isNullOrEmpty()) {
                showSnackbar(getString(R.string.readme_error_messsage))
            } else {
                binding.readmeWebView.loadUrl(readmeResponse!!.download_url)
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        finish()
    }
}