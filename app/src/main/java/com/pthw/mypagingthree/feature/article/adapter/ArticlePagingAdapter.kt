package com.pthw.mypagingthree.feature.article.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.pthw.domain.feature.article.model.Article
import com.pthw.mypagingthree.databinding.ArticleViewholderBinding
import com.pthw.mypagingthree.feature.article.viewholder.ArticleViewHolder

class ArticlePagingAdapter : PagingDataAdapter<Article, ArticleViewHolder>(
    ARTICLE_DIFF_CALLBACK
) {
    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ArticleViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }
}
