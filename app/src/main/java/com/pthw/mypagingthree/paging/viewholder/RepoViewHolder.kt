package com.pthw.mypagingthree.paging.viewholder

import android.view.View
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.base.recyclerview.BaseViewHolder
import com.pthw.mypagingthree.data.model.Repo
import com.pthw.mypagingthree.databinding.RepoViewItemBinding

class RepoViewHolder(private val binding: RepoViewItemBinding): BaseViewHolder<Repo>(binding.root) {
    override fun bind(item: Repo) {
        binding.repoName.text = item.fullName

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (item.description != null) {
            binding.repoDescription.text = item.description
            descriptionVisibility = View.VISIBLE
        }
        binding.repoDescription.visibility = descriptionVisibility

        binding.repoStars.text = item.stars.toString()
        binding.repoForks.text = item.forks.toString()

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!item.language.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            binding.repoLanguage.text = resources.getString(R.string.language, item.language)
            languageVisibility = View.VISIBLE
        }
        binding.repoLanguage.visibility = languageVisibility
    }
}