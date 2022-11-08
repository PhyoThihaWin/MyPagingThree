package com.pthw.listdialog.ui

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StyleRes
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.pthw.listdialog.base.BaseDialogFragment
import com.pthw.listdialog.databinding.FragmentSearchListDialogBinding
import com.pthw.listdialog.utils.afterTextChangedDelayed
import com.pthw.listdialog.utils.gone
import com.pthw.listdialog.utils.show
import com.pthw.listdialog.utils.textAppearance
import java.io.Serializable

const val MAX_ITEM = 10

class SearchListDialogFragment<Item : Any?>(
    private val onClick: (Int, Item) -> Unit,
) :
    BaseDialogFragment<FragmentSearchListDialogBinding>() {
    override fun bindView(inflater: LayoutInflater): FragmentSearchListDialogBinding {
        return FragmentSearchListDialogBinding.inflate(inflater)
    }

    private var items: List<Item> = emptyList()

    private var item: Item? = null

    private lateinit var itemAdapter: SearchListDialogAdapter<Item>


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
        val textStyle = arguments?.getInt("style", 0) ?: 0
        Log.i("item count is: ", "${items.size}")

        if (items.size > MAX_ITEM) {
            dialog?.window?.setLayout(width, height)
        } else {
            dialog?.window?.setLayout(width, LayoutParams.WRAP_CONTENT)
        }

        if (canSearch == true) {
            binding.tilSearch.show()
        } else {
            binding.tilSearch.gone()
        }

        if (textStyle != 0) {
            binding.etSearch.textAppearance(textStyle)
            binding.tvCancel.textAppearance(textStyle)
        }

        binding.tilSearch.hint = hint ?: "Search.."
        binding.etSearch.afterTextChangedDelayed { itemAdapter.filter.filter(it) }
        binding.tvCancel.setOnClickListener { dismiss() }

        setupList(items, textStyle)
    }

    private fun setupList(items: List<Item>, textStyle: Int) {
        itemAdapter = SearchListDialogAdapter(
            textStyle = textStyle,
            onSelected = { i, item ->
                onClick.invoke(i, item)
                dismiss()
            },
            listCount = {
                if (it > 0) binding.ivNoData.gone()
                else {
                    if (items.size > MAX_ITEM)
                        binding.ivNoData.show()
                }
            }
        )

        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItem.adapter = itemAdapter
        itemAdapter.setData(items.toMutableList())
        itemAdapter.setSelectionTracker(getSelectionTracker())
    }

}


interface GenericItem : Serializable {
    fun toMyString(): String
}

data class DialogConfigs<Item : Any?>(
    val list: ArrayList<Item>,
    val canSearch: Boolean = false,
    val hint: String? = null,
    @StyleRes val textStyle: Int = 0
)

fun <Item : Any?> FragmentManager.showSearchListDialog(
    dialogConfigs: DialogConfigs<Item>,
    onClick: (Int, Item) -> Unit
) {
    val bundle = Bundle()
    dialogConfigs.apply {
        bundle.putSerializable("list", list)
        bundle.putBoolean("search", canSearch)
        hint?.let { bundle.putString("hint", hint) }
        bundle.putInt("style", textStyle)
    }
    val dialog = SearchListDialogFragment(onClick)
    dialog.arguments = bundle
    dialog.show(this, "Search List Dialog")
}


/**
 * Sample Usage
 */
/*

  val configs = DialogConfigs(
        list = nationalities.toCollection(ArrayList()),
        canSearch = true,
        hint = "Search Nationality",
  )
  childFragmentManager.showSearchListDialog(configs) { item ->
       binding.tvNationality.text = item
  }

 data class TestModel(
    val id: String,
    val name: String
 ) : GenericItem {
    override fun toMyString(): String {
        return "Test: $name"
    }
 }
*/
