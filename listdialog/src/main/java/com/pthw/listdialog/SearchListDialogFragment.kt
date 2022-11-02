package com.pthw.listdialog

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import dev.onenex.heal.appbase.core.BaseDialogFragment
import dev.onenex.heal.appbase.extensions.afterTextChangedDelayed
import dev.onenex.heal.appbase.extensions.gone
import dev.onenex.heal.appbase.extensions.show
import dev.onenex.heal.databinding.FragmentSearchListDialogBinding
import dev.onenex.heal.features.dialog.*
import timber.log.Timber
import java.io.Serializable


class SearchListDialogFragment<Item : Any?>(
    private val onClick: (Item) -> Unit,
) :
    BaseDialogFragment<FragmentSearchListDialogBinding>() {
    override fun bindView(inflater: LayoutInflater): FragmentSearchListDialogBinding {
        return FragmentSearchListDialogBinding.inflate(inflater)
    }

    private var items: List<Item> = emptyList()

    private var item: Item? = null

    private val itemAdapter: SearchListDialogAdapter<Item> by lazy {
        SearchListDialogAdapter(
            {
                onClick.invoke(it)
                dismiss()
            },
            {
                if (it > 0) binding.ivNoData.gone()
                else binding.ivNoData.show()
            }
        )
    }


    private fun getSelectionTracker(): SelectionTracker<Long> {
        val selectionTracker = SelectionTracker.Builder(
            "tracker_id",
            binding.rvItem,
            KeyProvider(),
            DetailsClickLookup(binding.rvItem),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectSingleAnything()).build()
        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                selectionTracker.selection.map {
                    if (itemAdapter.itemCount > 0) {
                        item = itemAdapter.getItemAt(it.toInt())
                    }
                }
            }
        })


        return selectionTracker
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.70).toInt()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        items = arguments?.getSerializable("list") as List<Item>
        val hint = arguments?.getString("hint")
        val canSearch = arguments?.getBoolean("search", false)
        Timber.i("item count is: ${items.size}")

        if (canSearch == true) {
            binding.tilSearch.show()
        } else {
            binding.tilSearch.gone()
        }

        if (items.size > 10) {
            dialog?.window?.setLayout(width, height)
        } else {
            dialog?.window?.setLayout(width, LayoutParams.WRAP_CONTENT)
        }

        binding.tilSearch.hint = hint ?: "Search.."
        binding.etSearch.afterTextChangedDelayed {
            itemAdapter.filter.filter(it)
        }

        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItem.adapter = itemAdapter
        itemAdapter.setData(items.toMutableList())
        itemAdapter.setSelectionTracker(getSelectionTracker())

        binding.tvCancel.setOnClickListener { dismiss() }
    }

}

interface GenericItem : Serializable {
    fun toMyString(): String
}

fun <Item : Any?> SearchListDialogFragment<Item>.showList(
    fragmentManager: FragmentManager,
    list: ArrayList<Item>,
    canSearch: Boolean = false,
    hint: String? = null
) {
    val bundle = Bundle()
    bundle.putSerializable("list", list)
    bundle.putBoolean("search", canSearch)
    hint?.let { bundle.putString("hint", hint) }
    this.arguments = bundle
    this.show(fragmentManager, "Search List Dialog")
}


/**
 * Sample Usage
 */
/*
 SearchListDialogFragment<TestModel> {
    requireContext().showShortToast(it.toMyString())
 }.showList(childFragmentManager, list, hint = "Search User Ha Ha")

 SearchListDialogFragment<String?> {
    requireContext().showShortToast(it.orEmpty())
 }.showList(childFragmentManager, arr2.toCollection(kotlin.collections.ArrayList()))

 data class TestModel(
    val id: String,
    val name: String
 ) : GenericItem {
    override fun toMyString(): String {
        return "Test: $name"
    }
 }
*/
