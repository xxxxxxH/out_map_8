package net.basicmodel

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_preview.*


class PreviewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        val i = intent
        val url = i.getStringExtra("url")
        val webSettings: WebSettings = web.settings
        webSettings.javaScriptEnabled = true;
        webSettings.javaScriptCanOpenWindowsAutomatically = true;
        web.loadUrl(url!!)
    }
}