package com.pthw.mypagingthree.feature.firestorechat.activity.photoview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pthw.appbase.core.BaseFragment
import com.pthw.appbase.extension.loadFromUrl
import com.pthw.mypagingthree.databinding.FragmentPhotoBinding


class PhotoFragment : BaseFragment<FragmentPhotoBinding>() {

    override fun bindView(inflater: LayoutInflater): FragmentPhotoBinding {
        return FragmentPhotoBinding.inflate(inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photoUrl = arguments?.getString(ARGS_PHOTO_URL)
        photoUrl?.let {
            binding.ivImage.loadFromUrl(photoUrl)
        }
    }

    companion object {
        private const val ARGS_PHOTO_URL = "args.photo"
        fun newInstance(photoUrl: String): PhotoFragment {
            val fragment = PhotoFragment()
            val bundle = Bundle()
            bundle.putString(ARGS_PHOTO_URL, photoUrl)
            fragment.arguments = bundle
            return fragment
        }
    }
}