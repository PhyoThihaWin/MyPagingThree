package com.pthw.mypagingthree.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.*
import androidx.lifecycle.lifecycleScope
import com.pthw.appbase.core.BaseActivity
import com.pthw.appbase.extension.gone
import com.pthw.appbase.extension.show
import com.pthw.mypagingthree.databinding.ActivityWebViewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber


class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {
    override val binding: ActivityWebViewBinding by lazy {
        ActivityWebViewBinding.inflate(layoutInflater)
    }

    companion object {
        private const val EXTRA_WEB_URL = "extra_web_url"
        fun newIntent(context: Context, webUrl: String): Intent {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(EXTRA_WEB_URL, webUrl)
            return intent
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = createWebViewClient()
        binding.webView.loadUrl(intent.getStringExtra(EXTRA_WEB_URL) ?: "")
    }

    private fun createWebViewClient(): WebViewClient {
        return object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.clPlaceholder.show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Timber.i("onPageFinished %s", url)
                lifecycleScope.launch {
                    delay(1000)
                    binding.clPlaceholder.gone()
                }
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                Timber.i(
                    "onReceivedHttpError %s",
                    "${errorResponse?.statusCode}  ${errorResponse?.data}  ${errorResponse?.responseHeaders.toString()}  ${errorResponse?.mimeType} "
                )
                super.onReceivedHttpError(view, request, errorResponse)
            }

        }
    }
}