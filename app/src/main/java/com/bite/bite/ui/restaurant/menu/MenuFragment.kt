package com.bite.bite.ui.restaurant.menu

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bite.bite.R
import com.bite.bite.application.base.BaseFragment
import com.bite.bite.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_menu.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuFragment: BaseFragment(R.layout.fragment_menu){

    private val viewModel: MainViewModel by sharedViewModel()

    private val docsUrl = "https://docs.google.com/viewer?embedded=true&url="
    private val url = "https://www.pestocafe.ua/storage/tb-tree.node/2020/05/20/1589965514_pestocafe-menu-enet.pdf"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
//        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(docsUrl + url)
    }

}