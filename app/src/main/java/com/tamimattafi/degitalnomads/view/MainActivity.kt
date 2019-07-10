package com.tamimattafi.degitalnomads.view

import android.os.Bundle
import com.tamimattafi.degitalnomads.R
import com.tamimattafi.degitalnomads.view.fragments.home.MvpHomeFragment
import com.tamimattafi.degitalnomads.view.global.NavigationActivity

class MainActivity : NavigationActivity() {

    override val layoutId: Int = R.layout.activity_main
    override var rootView: Int = R.id.rootView


    override fun onViewCreated(savedInstanceState: Bundle?) {
        if (currentFragment == null) {
            requestAttachBaseScreen(MvpHomeFragment().also {
                it.attachFragmentManager(this)
            })
        }
    }
}
