package com.example.casinoslots.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.example.casinoslots.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    private lateinit var webView: WebView
    private var fileUploadCallback: ValueCallback<Array<Uri>>? = null
    private val fileChooserResultCode = 1
    private var customView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        webView = binding.idWebview
        setupWebView()
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            webView.loadUrl(intent.getStringExtra("url")!!)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webSettings.domStorageEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.databaseEnabled = true
        @Suppress("DEPRECATION")
        webSettings.databasePath = applicationContext.getDir("webview_databases", 0).path

        webSettings.allowFileAccess = true

        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.settings.loadsImagesAutomatically = true
        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                if (fileUploadCallback != null) {
                    fileUploadCallback!!.onReceiveValue(null)
                    fileUploadCallback = null
                }
                fileUploadCallback = filePathCallback
                val intent = fileChooserParams.createIntent()
                try {
                    @Suppress("DEPRECATION")
                    startActivityForResult(intent, fileChooserResultCode)
                } catch (e: Exception) {
                    fileUploadCallback = null
                    return false
                }
                return true
            }

            override fun onShowCustomView(view: View, callback: CustomViewCallback) {
                super.onShowCustomView(view, callback)
                if (customView != null) {
                    callback.onCustomViewHidden()
                    return
                }
                customView = view
                customView?.let {
                    val decorView = window.decorView as FrameLayout
                    decorView.addView(it, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                }
                webView.visibility = View.GONE
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                customView?.let {
                    val decorView = window.decorView as FrameLayout
                    decorView.removeView(it)
                    customView = null
                }
                webView.visibility = View.VISIBLE
            }

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileChooserResultCode) {
            if (fileUploadCallback != null) {
                val results = WebChromeClient.FileChooserParams.parseResult(resultCode, data)
                fileUploadCallback!!.onReceiveValue(results)
                fileUploadCallback = null
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finishAffinity()
        }
    }

}