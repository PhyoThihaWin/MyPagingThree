package com.pthw.appbase.core

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by Vincent on 12/6/18
 */

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    abstract val binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}

//abstract class BaseActivityWithState<VB : ViewBinding, VM : BaseViewModel<E>, E> :
//    BaseActivity<VB>() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        getOrCreateViewModel().eventLD.observe(this, ::renderEvent)
//    }
//
//    abstract fun getOrCreateViewModel(): VM
//
//    abstract fun renderEvent(event: E)
//}
