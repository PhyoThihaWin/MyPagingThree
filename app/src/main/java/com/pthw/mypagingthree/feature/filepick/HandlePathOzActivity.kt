package com.pthw.mypagingthree.feature.filepick

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import com.pthw.appbase.core.BaseActivity
import com.pthw.appbase.extension.isSdkBelowVersion
import com.pthw.listdialog.utils.inflater
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.databinding.ActivityHandlePathOzBinding
import com.pthw.mypagingthree.handle_path_oz.HandlePathOz
import com.pthw.mypagingthree.handle_path_oz.HandlePathOzListener
import com.pthw.mypagingthree.handle_path_oz.model.PathOz
import com.pthw.mypagingthree.helper.contracts_helper.ActivityIntentResultHelper
import com.pthw.mypagingthree.helper.contracts_helper.PermissionRequestHelper
import dev.onenex.heal.appbase.filepath.utils.extension.getListUri
import kotlinx.coroutines.FlowPreview
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import timber.log.Timber
import java.io.File

class HandlePathOzActivity : BaseActivity<ActivityHandlePathOzBinding>() {

    override val binding: ActivityHandlePathOzBinding by lazy {
        ActivityHandlePathOzBinding.inflate(inflater())
    }

    // ResultHelper
    private val activityResultHelper = ActivityIntentResultHelper(this)

    // Permission Helper
    private val permissionHelper = PermissionRequestHelper(this)

    // Permission List
    private val permissions by lazy {
        if (isSdkBelowVersion(Build.VERSION_CODES.S_V2))
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        else arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO
        )
    }

    private val handlePathOzListener = object : HandlePathOzListener.MultipleUri {
        override fun onRequestHandlePathOz(listPathOz: List<PathOz>, tr: Throwable?) {
            listPathOz.map {
                Timber.i("File paths %s", File(it.path).absolutePath)
            }
//            viewModel.uploadChatFile(listPathOz.map { it.path }, chatExtraData.chatId)
        }
    }

    private val handlePathOz by lazy {
        HandlePathOz(this, handlePathOzListener)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpCarouselSlider()
        binding.setUpUIListener()

    }

    private fun ActivityHandlePathOzBinding.setUpUIListener() {
        btnGalleryPick.setOnClickListener {
            permissionHelper.launch(permissions) {
                Timber.i("Gallery $it")
                if (it) callGalleryIntent()
                else permissionHelper.shouldShowPermissionRequest()
            }
        }

        btnExplorerPick.setOnClickListener {
            permissionHelper.launch(permissions) {
                if (it) callFileExplorerIntent()
            }
        }
    }

    private fun callFileExplorerIntent() {
        val mimeTypes = arrayOf(
            "image/jpeg",
            "image/png",
            "image/jpg",
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        )

        val pickFileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)

        pickFileIntent.type = "*/*"
        pickFileIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        pickFileIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pickFileIntent.addCategory(Intent.CATEGORY_OPENABLE)
        activityResultHelper.launch(pickFileIntent) {
            prepareResultForFileUpload(it)
        }
    }

    private fun callGalleryIntent() {
        val pickImageIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickImageIntent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        pickImageIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        pickImageIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        activityResultHelper.launch(pickImageIntent) {
            prepareResultForFileUpload(it)
        }
    }

    private fun setUpCarouselSlider() {
        // Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragment it will be viewLifecycleOwner/getViewLifecycleOwner().
        binding.carousel.registerLifecycle(lifecycle)
        val list = mutableListOf<CarouselItem>()

        list.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
                caption = "Photo by Aaron Wu on Unsplash"
            )
        )

        list.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080"
            )
        )
        binding.carousel.setData(list)
    }

    @OptIn(FlowPreview::class)
    private fun prepareResultForFileUpload(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val listUri = it.getListUri()
                handlePathOz.getListRealPath(listUri)
            }
        }

    }

}