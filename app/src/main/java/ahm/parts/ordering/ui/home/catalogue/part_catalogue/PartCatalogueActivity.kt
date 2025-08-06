package ahm.parts.ordering.ui.home.catalogue.part_catalogue

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.catalogue.PartCatalogue
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_part_catalogue.*
import kotlinx.android.synthetic.main.content_loading_list.*

class PartCatalogueActivity : BaseActivity<HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_catalogue)

        initUI()

    }

    private fun initUI(){
        val partCatalogue = extra(Constants.REMOTE.OBJ_DATA).getObject<PartCatalogue>()

        setToolbar(partCatalogue.name,true)

        setWebView(partCatalogue.detail)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url : String){
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                showToast(description)
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(
                    view,
                    rerr.getErrorCode(),
                    rerr.getDescription().toString(),
                    req.getUrl().toString()
                )

                lLoadingView.hide()
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // TODO Auto-generated method stub
                view.loadUrl(url)
                lLoadingView.show()
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url)
                try {
                    lLoadingView.hide()
                } catch (e: Exception) {
                }
            }

        }

        webView.loadUrl(url)

    }

}
