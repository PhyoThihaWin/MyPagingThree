package com.pthw.mypagingthree.feature.article.viewholder

import com.pthw.appbase.core.recyclerview.BaseViewHolder
import com.pthw.domain.feature.article.model.Article
import com.pthw.mypagingthree.databinding.ArticleViewholderBinding

class ArticleViewHolder(
    private val binding: ArticleViewholderBinding
) : BaseViewHolder<Article>(binding.root) {
    override fun bind(item: Article) {
        binding.apply {
            binding.apply {
                binding.title.text = item.title
                binding.description.text = item.description
                binding.created.text = item.createdText
            }
        }
    }
}
