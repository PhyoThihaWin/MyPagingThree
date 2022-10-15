package com.pthw.mypagingthree.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Vincent on 3/1/19
 */
abstract class BaseSheetDialogFragment<VB : ViewBinding> :
  BottomSheetDialogFragment() {

  private var _binding: VB? = null

  protected val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): android.view.View? {
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    _binding = bindView(inflater)
    val view = binding.root
    return view
  }

  abstract fun bindView(inflater: LayoutInflater): VB

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

}


