package com.pthw.mypagingthree.feature.samplepost.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.pthw.appbase.core.BaseActivity
import com.pthw.appbase.core.fastadapter.bind
import com.pthw.appbase.core.fastadapter.update
import com.pthw.appbase.core.viewstate.ListViewState
import com.pthw.appbase.core.viewstate.renderState
import com.pthw.appbase.extension.openActivity
import com.pthw.domain.feature.samplepost.model.Post
import com.pthw.listdialog.utils.gone
import com.pthw.listdialog.utils.show
import com.pthw.listdialog.utils.showShortToast
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.databinding.ActivitySamplePostBinding
import com.pthw.mypagingthree.databinding.ListItemSamplePostBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SamplePostActivity : BaseActivity<ActivitySamplePostBinding>() {
    override val binding: ActivitySamplePostBinding by lazy {
        ActivitySamplePostBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SamplePostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUI()

        lifecycleScope.launch {
            viewModel.postFlow.collectLatest {
                it.renderState(
                    loading = {
                        binding.progressBar.show()
                    },
                    success = { list ->
                        binding.progressBar.gone()
                        binding.rvPost.update(list.toMutableList())
                    },
                    error = { msg ->
                        binding.progressBar.gone()
                        this@SamplePostActivity.showShortToast(msg)
                    }
                )
            }
        }
        viewModel.getLatestPost()
    }

    private fun setupUI() {
        binding.rvPost.bind(
            emptyList<Post>(),
            R.layout.list_item_sample_post
        ) { item, pos ->
            ListItemSamplePostBinding.bind(this).apply {
                tvTitle.text = item.title
                tvBody.text = item.body

                this.root.setOnClickListener {
                    openActivity(PostDetailActivity::class.java) {
                        putSerializable("post", item)
                    }
                }
            }
        }
    }


}

