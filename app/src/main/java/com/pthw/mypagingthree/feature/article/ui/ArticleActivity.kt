package com.pthw.mypagingthree.feature.article.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pthw.appbase.core.BaseActivity
import com.pthw.mypagingthree.databinding.ActivityArticleBinding
import com.pthw.mypagingthree.feature.article.adapter.ArticlePagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding>() {

    override val binding: ActivityArticleBinding by lazy {
        ActivityArticleBinding.inflate(layoutInflater)
    }

    private val viewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = viewModel.items
        val articleAdapter = ArticlePagingAdapter()

        binding.bindAdapter(articleAdapter = articleAdapter)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    articleAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                articleAdapter.loadStateFlow.collect {
                    binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading

                    Timber.i(articleAdapter.itemCount.toString() + "ggg")

                }
            }
        }
    }

}

private fun ActivityArticleBinding.bindAdapter(articleAdapter: ArticlePagingAdapter) {
    list.adapter = articleAdapter
    list.layoutManager = LinearLayoutManager(list.context)
    val decoration = DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL)
    list.addItemDecoration(decoration)
}