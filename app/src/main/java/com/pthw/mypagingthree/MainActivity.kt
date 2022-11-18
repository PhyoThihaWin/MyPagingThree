package com.pthw.mypagingthree

import android.os.Bundle
import com.pthw.appbase.core.BaseActivity
import com.pthw.appbase.extension.openActivity
import com.pthw.listdialog.ui.DialogConfigs
import com.pthw.listdialog.ui.showSearchListDialog
import com.pthw.listdialog.utils.showShortToast
import com.pthw.mypagingthree.databinding.ActivityMainBinding
import com.pthw.mypagingthree.feature.article.ui.ArticleActivity
import com.pthw.mypagingthree.feature.githubrepo.ui.SearchRepositoriesActivity
import com.pthw.mypagingthree.feature.samplepost.ui.SamplePostActivity
import com.pthw.mypagingthree.feature.splashimage.ui.SplashPhotoActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnArticle.setOnClickListener {
            openActivity(ArticleActivity::class.java)
        }

        binding.btnSplashImage.setOnClickListener {
            openActivity(SplashPhotoActivity::class.java)
        }

        binding.btnGithubRepo.setOnClickListener {
            openActivity(SearchRepositoriesActivity::class.java)
        }

        binding.btnSamplePost.setOnClickListener {
            openActivity(SamplePostActivity::class.java)
        }

        binding.btnListDialog.setOnClickListener {
            val list = arrayListOf(
                "Iron Man",
                "Super Man",
                "Spider Man",
                "Baby Man",
                "Captain Marvel",
                "Captain America", "Spider Man",
                "Baby Man",
                "Captain Marvel",
                "Captain America",
                "Captain America"
            )

            val configs = DialogConfigs(
                list = list,
                canSearch = true,
                hint = "Search Avengers",
                textStyle = R.style.TextViewFontBas
            )
            supportFragmentManager.showSearchListDialog(configs) { i, item ->
                showShortToast(item)
            }
        }

    }


}