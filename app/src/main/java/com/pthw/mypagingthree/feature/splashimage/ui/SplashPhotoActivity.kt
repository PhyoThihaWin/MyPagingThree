package com.pthw.mypagingthree.feature.splashimage.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import com.pthw.appbase.core.BaseActivity
import com.pthw.appbase.extension.showShortToast
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.databinding.ActivitySplashImageBinding
import com.pthw.mypagingthree.feature.splashimage.adapter.SplashPhotoHeaderAdapter
import com.pthw.mypagingthree.feature.splashimage.adapter.SplashPhotoLoadStateAdapter
import com.pthw.mypagingthree.feature.splashimage.adapter.SplashPhotoPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SplashPhotoActivity : BaseActivity<ActivitySplashImageBinding>() {

    override val binding: ActivitySplashImageBinding by lazy {
        ActivitySplashImageBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SplashPhotoViewModel>()
    private lateinit var adapter: SplashPhotoPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = SplashPhotoPagingAdapter {
            showShortToast(it.user.username)
        }
        val headerAdapter = SplashPhotoHeaderAdapter()
        binding.apply {
            recyclerView.setHasFixedSize(true)
            val mAdapter = adapter.withLoadStateHeaderAndFooter(
                header = SplashPhotoLoadStateAdapter { adapter.retry() },
                footer = SplashPhotoLoadStateAdapter { adapter.retry() },
            )
            recyclerView.adapter = ConcatAdapter(headerAdapter, mAdapter)
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.pagingFlow.collectLatest {
                    Timber.w("Reached collect state!!")
                    adapter.submitData(it)
                }
            }
        }
        viewModel.searchPhotos("cats")

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                adapter.loadStateFlow.collect {
//                    binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
//                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading
//
//                    showShortToast("Paging is in loading state.")
//                }
//            }
//        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }

                if (loadState.source.refresh is LoadState.Error) {
                    val errorState = loadState.source.refresh as LoadState.Error
                    val errorMessage = viewModel.mapException(errorState.error)
                    showShortToast(errorMessage)
                    Timber.e(errorState.error)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_splash, menu)

        menu?.let {
            val searchItem = it.findItem(R.id.action_search)
            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    if (query != null) {
                        binding.recyclerView.scrollToPosition(0)
                        viewModel.searchPhotos(query)
                        searchView.clearFocus()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }

        return true
    }
}
