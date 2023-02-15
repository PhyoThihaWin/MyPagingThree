package com.pthw.mypagingthree.feature.samplepost.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.pthw.appbase.core.BaseActivity
import com.pthw.appbase.core.fastadapter.bind
import com.pthw.appbase.core.fastadapter.update
import com.pthw.appbase.core.viewstate.renderState
import com.pthw.domain.feature.samplepost.model.Comment
import com.pthw.domain.feature.samplepost.model.Post
import com.pthw.listdialog.utils.gone
import com.pthw.listdialog.utils.show
import com.pthw.listdialog.utils.showShortToast
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.databinding.ActivityPostDetailBinding
import com.pthw.mypagingthree.databinding.ListItemPostCommentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Timer

@AndroidEntryPoint
class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>() {

    override val binding: ActivityPostDetailBinding by lazy {
        ActivityPostDetailBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<PostDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUI()

        lifecycleScope.launch {
            viewModel.cmFlow.collectLatest {
                it.renderState(
                    loading = {
                        binding.progressBar.show()
                    },
                    success = { data ->
                        binding.progressBar.gone()
                        binding.rvComment.update(data.toMutableList())
                    },
                    error = { msg ->
                        binding.progressBar.gone()
                        showShortToast(msg)
                    },
                )
            }
        }

        viewModel.post?.let {
            viewModel.getPostDetail(it.id)
        }
    }

    private fun setupUI() {
        viewModel.post = intent.getSerializableExtra("post") as Post
        binding.tvTitle.text = viewModel.post?.title
        binding.tvBody.text = viewModel.post?.body

        binding.rvComment.bind(
            emptyList<Comment>(),
            R.layout.list_item_post_comment
        ) { item, pos ->
            ListItemPostCommentBinding.bind(this).apply {
                tvName.text = item.name
                tvEmail.text = item.email
                tvBody.text = item.body
            }
        }
    }
}
