package com.pthw.mypagingthree.feature.firestorechat.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.pthw.appbase.core.BaseActivity
import com.pthw.appbase.core.fastadapter.SmartScrollListener
import com.pthw.appbase.core.viewstate.renderState
import com.pthw.appbase.extension.*
import com.pthw.mypagingthree.databinding.ActivityChattingBinding
import com.pthw.mypagingthree.handle_path_oz.HandlePathOz
import com.pthw.mypagingthree.handle_path_oz.HandlePathOzListener
import com.pthw.mypagingthree.handle_path_oz.model.PathOz
import com.pthw.mypagingthree.helper.contracts_helper.ActivityIntentResultHelper
import com.pthw.mypagingthree.helper.contracts_helper.PermissionRequestHelper
import dagger.hilt.android.AndroidEntryPoint
import dev.onenex.heal.appbase.filepath.utils.extension.getListUri
import com.pthw.mypagingthree.feature.firestorechat.APPOINTMENT_ID_KEY
import com.pthw.mypagingthree.feature.firestorechat.PARENT_COLLECTION
import com.pthw.mypagingthree.feature.firestorechat.SUB_COLLECTION
import com.pthw.mypagingthree.feature.firestorechat.lifecycleaware.ChattingViewModel
import com.pthw.mypagingthree.feature.firestorechat.model.Attachment
import com.pthw.mypagingthree.feature.firestorechat.model.ChatDocumentRef
import dev.onenex.heal.features.firestorechat.model.ChattingViewState
import com.pthw.mypagingthree.feature.firestorechat.model.MessageData
import com.pthw.mypagingthree.feature.firestorechat.smoothScrollToPos

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ChattingActivity : BaseActivity<ActivityChattingBinding>(),
    SmartScrollListener.OnSmartScrollListener {

    companion object {
        private const val CHAT_EXTRA = "extra_chat"
        fun newIntent(context: Context, chatExtra: ChatExtra): Intent {
            val intent = Intent(context, ChattingActivity::class.java)
            intent.putExtra(CHAT_EXTRA, chatExtra)
            return intent
        }
    }

    override val binding: ActivityChattingBinding by lazy {
        ActivityChattingBinding.inflate(layoutInflater)
    }

    private val chattingAdapter by lazy { ChattingAdapter() }
    private val viewModel by viewModels<ChattingViewModel>()

    private lateinit var handlePathOz: HandlePathOz

    @Inject
    lateinit var firestore: FirebaseFirestore
    lateinit var documentRef: DocumentReference
    lateinit var collectionRef: CollectionReference

    private val activityResultHelper = ActivityIntentResultHelper(this)
    private val permissionHelper = PermissionRequestHelper(this)
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


    private val chatExtraData: ChatExtra by lazy {
        intent.getSerializableExtra(CHAT_EXTRA) as ChatExtra
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** pass appointment id to viewModel */
        viewModel.setAppointmentId(chatExtraData.chatId)

        /** Fire-store setup */
        documentRef = firestore.collection(PARENT_COLLECTION).document(chatExtraData.chatId)
        collectionRef = documentRef.collection(SUB_COLLECTION)
        initAppointmentIdRef()

        binding.setupUI()
        renderChatData()
//        collectFlows()

        /** FireStore Default auth for permission rule */
//        Firebase.auth.signOut()
        signInWithAdminAcc()
        handlePathOz = HandlePathOz(this, handlePathOzListener)

    }


    private val handlePathOzListener = object : HandlePathOzListener.MultipleUri {
        override fun onRequestHandlePathOz(listPathOz: List<PathOz>, tr: Throwable?) {
            listPathOz.map {
                Timber.i(File(it.path).absolutePath)
            }
//            viewModel.uploadChatFile(listPathOz.map { it.path }, chatExtraData.chatId)
        }
    }

    private fun ActivityChattingBinding.setupUI() {
        ivBackArrow.setOnClickListener { finish() }
        tvDoctorName.text = chatExtraData.doctorName
        val myLayoutManager =
            LinearLayoutManager(this@ChattingActivity, LinearLayoutManager.VERTICAL, true)
        rvChatting.apply {
            layoutManager = myLayoutManager
            setHasFixedSize(true)
            adapter = chattingAdapter.asPaginationAdapter()
            applySmartScrollListener(this@ChattingActivity, UP_SCROLL_DIRECTION)
        }

        /** Detect chat expired */
        if (chatExtraData.activate) {
            layoutChatInput.show()
            tvChatExpired.hide()
        } else {
            layoutChatInput.gone()
            tvChatExpired.show()
        }

        uiListener()
    }

    private fun ActivityChattingBinding.uiListener() {
        ivSend.setOnClickListener {
            if (etMessage.text.isEmpty()) return@setOnClickListener
            /** Prepare message */
            val chatData = MessageData(
                sender_id = viewModel.userId.toString(),
                message = etMessage.text.toString(),
                sender_name = viewModel.userId.toString(),
                attachments = emptyList(),
                read_by = viewModel.readBy
            )
            sendMessage(chatData)
        }

        ivGallery.setOnClickListener {
            permissionHelper.launch(permissions) {
                Timber.i("Gallery $it")
                if (it) callGalleryIntent()
            }
        }

        ivAttach.setOnClickListener {
            permissionHelper.launch(permissions) {
                if (it) callFileExplorerIntent()
            }
        }

        etMessage.afterTextChangedDelayed {
            if (it.isEmpty()) {
                ivGallery.show()
                ivAttach.show()
                ivSend.gone()
            } else {
                ivGallery.gone()
                ivAttach.gone()
                ivSend.show()
            }
        }
    }

    /** Initialize Appointment Id in fire-store */
    private fun initAppointmentIdRef() {
        if (chatExtraData.appointmentId == null) return
        documentRef.get().addOnSuccessListener {
            if (it.get(APPOINTMENT_ID_KEY) == null) {
                documentRef.set(ChatDocumentRef(appointment_id = chatExtraData.appointmentId.toString()))
                    .addOnSuccessListener {
                        Timber.i("Set appointment_id successfully.")
                    }
            }
        }
    }

    /** Send message to fire-store */
    private fun sendMessage(chatData: MessageData) {
        collectionRef.add(chatData)
            .addOnSuccessListener {
//                viewModel.notifyMessage(chatExtraData.chatId, chatData.message ?: "")
                Timber.i("Message sent to FireStore.")
            }.addOnFailureListener {
                Timber.e(it)
            }

        binding.etMessage.setText("")
        binding.rvChatting.smoothScrollToPos(0)
    }


    private fun renderChatData() {
        viewModel.getChatListLiveData {
            it?.observe(this) { state ->
                when (state) {
                    is ChattingViewState.SetMessageData -> {
                        chattingAdapter.setNewData(state.data.toMutableList())
                        Timber.i("NotifyDataSetChanged !!")
                    }
                    is ChattingViewState.ReachedEndState -> {
                        chattingAdapter.lastItemReached()
                    }
                    is ChattingViewState.ErrorState -> {
                        Timber.e(state.msg)
                    }
                    is ChattingViewState.EmptyChatState -> {
                        if (state.isEmpty && chattingAdapter.itemCount < 1)
                            binding.ivNoData.show()
                        else binding.ivNoData.hide()
                    }
                }
                if (binding.loading.isVisible) binding.loading.hide()
            }
        }
    }

    override fun onListEndReach() {
        if (chattingAdapter.isLastReached) return
        chattingAdapter.showFooterLoading()
        renderChatData()
    }

    override fun onResume() {
        super.onResume()
        //false for closing noti when user is in chat screen
//        viewModel.updateChatNotifySetting(false)
        hideKeyboard()
    }

    override fun onPause() {
        //true for closing noti when user is in chat screen
//        viewModel.updateChatNotifySetting(true)
        super.onPause()
    }


    private fun callFileExplorerIntent() {
        val mimeTypes = arrayOf(
//            "image/jpeg",
//            "image/png",
//            "image/jpg",
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        )

//        val intent = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
//            Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
//        } else {
//            Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
//        }

//        intent.apply {
//            type = "*/*"
//            action = Intent.ACTION_GET_CONTENT
//            action = Intent.ACTION_OPEN_DOCUMENT
//            addCategory(Intent.CATEGORY_OPENABLE)
//            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            putExtra("return-data", true)
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        }

        val pickFileIntent = Intent(
            Intent.ACTION_OPEN_DOCUMENT
        )

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

    @OptIn(FlowPreview::class)
    private fun prepareResultForFileUpload(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val listUri = it.getListUri()
                handlePathOz.getListRealPath(listUri)
            }
        }

    }

//    private fun collectFlows() {
//        lifecycleScope.launch {
//            viewModel.chatFilesFlow.collectLatest {
//                it.renderState(
//                    loading = {
//                        binding.loading.show()
//                    },
//                    success = { item ->
//                        binding.loading.gone()
//                        val filesList = mutableListOf<Attachment>()
//                        item.forEach { chatFile ->
//                            val type = chatFile.contentType.split("/")[0]
//                            filesList.add(
//                                Attachment(
//                                    type = type,
//                                    url = chatFile.url,
//                                    name = chatFile.fileName
//                                )
//                            )
//                        }
//                        /** Prepare message */
//                        val chatData = MessageData(
//                            sender_id = viewModel.user?.userId.toString(),
//                            sender_name = viewModel.user?.name,
//                            attachments = filesList,
//                            read_by = viewModel.readBy
//                        )
//                        sendMessage(chatData)
//                    },
//                    error = { msg ->
//                        binding.loading.gone()
//                        showShortToast(msg)
//                    }
//                )
//            }
//        }
//    }

    /** Firebase Auth for FireStore permission rule */
    private fun signInWithAdminAcc() {
        Firebase.auth.signInWithEmailAndPassword("phyothiha@onenex.co", "123456")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithEmail:success")
//                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.e("signInWithEmail:failure", task.exception)
//                    Toast.makeText(baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
    }


}




