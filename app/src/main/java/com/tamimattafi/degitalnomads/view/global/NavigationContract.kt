package com.tamimattafi.degitalnomads.view.global

import android.content.Intent
import androidx.fragment.app.Fragment


interface NavigationContract {

    interface FragmentManager {
        var rootView : Int
        fun requestAttachBaseScreen(fragment: Fragment)
        fun requestSlideLeftScreen(fragment: Fragment, name: String)
        fun requestBackPress()
        fun startActivityForResult(intent : Intent)
    }

    interface RequestProvider {
        var fragmentManager : FragmentManager?
        fun attachFragmentManager(fragmentManager: FragmentManager?) {
            this.fragmentManager = fragmentManager
        }
    }

    interface BackPressController {
        fun onBackPressed() : Boolean
    }

    interface ActivityResultReceiver {
        fun onActivityResult(requestCode : Int, resultCode : Int, data: Intent?)
    }


}