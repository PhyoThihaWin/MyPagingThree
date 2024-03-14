package com.pthw.mypagingthree.feature.firestorechat.activity.photoview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.pthw.appbase.core.BaseActivity
import com.pthw.mypagingthree.databinding.ActivityImagePagerBinding
import com.pthw.appbase.extension.reduceDragSensitivity
import timber.log.Timber

class ImagePagerActivity : BaseActivity<ActivityImagePagerBinding>() {

    companion object {
        private const val EXTRA_PHOTO_LIST = "extra.photo.list"
        private const val EXTRA_VIEW_POSITION = "extra.view.position"

        fun newIntent(context: Context, list: ArrayList<String>, viewPosition: Int): Intent {
            val intent = Intent(context, ImagePagerActivity::class.java)
            intent.putExtra(EXTRA_VIEW_POSITION, viewPosition)
            intent.putStringArrayListExtra(EXTRA_PHOTO_LIST, list)
            return intent
        }
    }

    private lateinit var onPageChangeCallBack: ViewPager2.OnPageChangeCallback

    private lateinit var adapter: ViewPagerAdapter

    private lateinit var fragmentList: ArrayList<Fragment>

    override val binding: ActivityImagePagerBinding by lazy {
        ActivityImagePagerBinding.inflate(
            layoutInflater
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val photo = intent.getStringArrayListExtra(EXTRA_PHOTO_LIST)
        val viewPosition = intent.getIntExtra(EXTRA_VIEW_POSITION, 0)

        fragmentList = ArrayList()
        photo?.forEach {
            Timber.i(it)
            fragmentList.add(PhotoFragment.newInstance(photoUrl = it))
        }
        Timber.i(fragmentList.size.toString())
        Timber.i(viewPosition.toString())

        updatePhotoCount(viewPosition)

        adapter = ViewPagerAdapter(this, fragmentList)
        binding.vpImage.adapter = adapter
        binding.vpImage.setCurrentItem(viewPosition, false)
        binding.vpImage.reduceDragSensitivity(3)
        onPageChangeCallBack = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Timber.i(position.toString())
                updatePhotoCount(position)
            }
        }
        binding.vpImage.registerOnPageChangeCallback(onPageChangeCallBack)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        fragmentList.clear()
        binding.vpImage.unregisterOnPageChangeCallback(onPageChangeCallBack)
        super.onDestroy()
    }

    private fun updatePhotoCount(imagePosition: Int) {
        binding.tvPhotoCount.text = "${imagePosition + 1}/${fragmentList.size}"
    }
}