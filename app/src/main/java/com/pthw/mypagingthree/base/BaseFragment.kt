package com.pthw.mypagingthree.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by Vincent on 12/6/18
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

  private var _binding: VB? = null

  protected val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): android.view.View? {
    //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
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
