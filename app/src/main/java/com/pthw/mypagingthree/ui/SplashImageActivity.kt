package com.pthw.mypagingthree.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.base.BaseActivity
import com.pthw.mypagingthree.data.api.SplashPhotoService
import com.pthw.mypagingthree.databinding.ActivitySplashImageBinding
import com.pthw.mypagingthree.paging.SplashPhotoLoadStateAdapter
import com.pthw.mypagingthree.paging.SplashPhotoPagingAdapter
import com.pthw.mypagingthree.utils.ext.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SplashImageActivity : BaseActivity<ActivitySplashImageBinding>() {
    override val binding: ActivitySplashImageBinding by lazy {
        ActivitySplashImageBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SplashImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = SplashPhotoPagingAdapter()
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = SplashPhotoLoadStateAdapter { adapter.retry() },
                footer = SplashPhotoLoadStateAdapter { adapter.retry() },
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                Timber.e("Reached started again!!")
                viewModel.searchPhotos("cats").collectLatest {
                    adapter.submitData(it)
                }
            }
        }

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

    }


}