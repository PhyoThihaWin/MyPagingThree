package com.pthw.mypagingthree.feature.firestore_chat

import android.os.Bundle
import android.widget.AbsListView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.pthw.appbase.core.BaseActivity
import com.pthw.appbase.core.fastadapter.SmartScrollListener
import com.pthw.mypagingthree.databinding.ActivityChattingBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ChattingActivity : BaseActivity<ActivityChattingBinding>(),
    SmartScrollListener.OnSmartScrollListener {

    override val binding: ActivityChattingBinding by lazy {
        ActivityChattingBinding.inflate(layoutInflater)
    }

    private val chattingAdapter by lazy { ChattingAdapter() }
    private var isScrolling = false

    @Inject
    lateinit var firestore: FirebaseFirestore
    private val viewModel by viewModels<ChattingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.setupUI()
        renderChatData()

//        initRecyclerViewOnScrollListener()

    }

    private fun ActivityChattingBinding.setupUI() {
        val myLayoutManager =
            LinearLayoutManager(this@ChattingActivity, LinearLayoutManager.VERTICAL, true)
        rvChatting.apply {
            layoutManager = myLayoutManager
            setHasFixedSize(true)
            adapter = chattingAdapter
            addOnScrollListener(SmartScrollListener(this@ChattingActivity,-1))
        }

        ivSend.setOnClickListener {
            if (etMessage.text.isEmpty()) return@setOnClickListener

            val chatData = ChatData(
                messageType = "text", messageData = MessageData(
                    id = "ggg",
                    message = etMessage.text.toString(),
                    dateTime = System.currentTimeMillis()
                )
            )

            sendMessage(chatData)
            etMessage.setText("")
            rvChatting.smoothScrollToPos(0)
        }
    }


    private fun sendMessage(chatData: ChatData) {
        val documentRef = firestore.collection("/Chat").document("appointment_1")
        val collection = documentRef.collection("message")
        collection.add(chatData)
            .addOnSuccessListener {
                Timber.w("Chat data has send to FireStore.")
            }
            .addOnFailureListener {
                Timber.w("Fail to send chat data!")
            }
    }


    private fun renderChatData() {
        viewModel.getChatListLiveData()?.observe(this) {
            when (it) {
                is ChattingViewState.AddOldDataState -> {
                    chattingAdapter.addNewData(it.data)
                    Timber.w("Reached added old item, ${it.data}")
                }
                is ChattingViewState.AddNewDataState -> {
                    chattingAdapter.addDataAtPositionZero(it.data)
                    Timber.w("Reached added new item, ${it.data}")
                }
                is ChattingViewState.ModifyDataState -> {
                    modifyProduct(it.data)
                    Timber.w("Reached modified!, ${it.data}")
                }
                is ChattingViewState.RemoveDataState -> {
                    removeProduct(it.data)
                    Timber.w("Reached removed!, ${it.data}")
                }
            }
//            binding.loading.gone()
//            binding.loadingTop.gone()
        }
    }


    private fun modifyProduct(modifiedProduct: ChatMessage) {
//        for (i in productList.indices) {
//            val currentProduct: Product = productList.get(i)
//            if (currentProduct.id.equals(modifiedProduct.id)) {
//                productList.remove(currentProduct)
//                productList.add(i, modifiedProduct)
//            }
//        }
    }

    private fun removeProduct(removedProduct: ChatMessage) {
//        for (i in productList.indices) {
//            val currentProduct: Product = productList.get(i)
//            if (currentProduct.id.equals(removedProduct.id)) {
//                productList.remove(currentProduct)
//            }
//        }
    }

    private fun initRecyclerViewOnScrollListener() {
        val onScrollListener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScrolling = true
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    if (layoutManager != null) {
                        val firstVisibleProductPosition =
                            layoutManager.findFirstVisibleItemPosition()
                        val visibleProductCount = layoutManager.childCount
                        val totalProductCount = layoutManager.itemCount
                        if (isScrolling && (firstVisibleProductPosition + visibleProductCount == totalProductCount)) {
                            isScrolling = false
//                            renderChatData()
                            Timber.w("Reach end scroll top.")
                        }

                    }
                }
            }
        binding.rvChatting.addOnScrollListener(onScrollListener)
    }

    override fun onListEndReach() {
//        binding.loadingTop.show()
        renderChatData()
        Timber.w("Reach end scroll top.")
    }

}


