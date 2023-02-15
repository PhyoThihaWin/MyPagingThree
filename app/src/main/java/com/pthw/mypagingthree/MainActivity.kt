package com.pthw.mypagingthree

import android.os.Bundle
import com.pthw.appbase.core.BaseActivity
import com.pthw.appbase.extension.openActivity
import com.pthw.listdialog.ui.DialogConfigs
import com.pthw.listdialog.ui.showSearchListDialog
import com.pthw.listdialog.utils.showShortToast
import com.pthw.mypagingthree.databinding.ActivityMainBinding
import com.pthw.mypagingthree.feature.article.ui.ArticleActivity
import com.pthw.mypagingthree.feature.firestore_chat.ChattingActivity
import com.pthw.mypagingthree.feature.githubrepo.ui.SearchRepositoriesActivity
import com.pthw.mypagingthree.feature.modern_storage.ModernStorageActivity
import com.pthw.mypagingthree.feature.samplepost.ui.SamplePostActivity
import com.pthw.mypagingthree.feature.splashimage.ui.SplashPhotoActivity
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

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
                textStyle = R.style.TextViewFontBase
            )
            supportFragmentManager.showSearchListDialog(configs) { i, item ->
                showShortToast(item)
            }
        }

        binding.btnModernStorage.setOnClickListener {
            openActivity(ModernStorageActivity::class.java)
        }

        binding.btnFirestoreChat.setOnClickListener {
            openActivity(ChattingActivity::class.java)
        }



// Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragment it will be viewLifecycleOwner/getViewLifecycleOwner().
        binding.carousel.registerLifecycle(lifecycle)

        val list = mutableListOf<CarouselItem>()

// Image URL with caption
        list.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
                caption = "Photo by Aaron Wu on Unsplash"
            )
        )

// Just image URL
        list.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080"
            )
        )

// Image URL with header
//        val headers = mutableMapOf<String, String>()
//        headers["header_key"] = "header_value"
//
//        list.add(
//            CarouselItem(
//                imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080",
//                headers = headers
//            )
//        )

//// Image drawable with caption
//        list.add(
//            CarouselItem(
//                imageDrawable = R.drawable.image_1,
//                caption = "Photo by Kimiya Oveisi on Unsplash"
//            )
//        )
//
//// Just image drawable
//        list.add(
//            CarouselItem(
//                imageDrawable = R.drawable.image_2
//            )
//        )

// ...

        binding.carousel.setData(list)
    }
}
