package com.pthw.mypagingthree.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pthw.mypagingthree.data.model.Article
import com.pthw.mypagingthree.data.model.createdText
import com.pthw.mypagingthree.databinding.ArticleViewholderBinding

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

class ArticleViewHolder(
    private val binding: ArticleViewholderBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article) {
        binding.apply {
            binding.apply {
                binding.title.text = article.title
                binding.description.text = article.description
                binding.created.text = article.createdText
            }
        }
    }

}