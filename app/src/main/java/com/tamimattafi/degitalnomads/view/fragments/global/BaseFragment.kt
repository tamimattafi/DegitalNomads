package com.tamimattafi.degitalnomads.view.fragments.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tamimattafi.degitalnomads.view.global.NavigationContract

abstract class BaseFragment : Fragment(), NavigationContract.RequestProvider {

    abstract val layoutId : Int
    override var fragmentManager : NavigationContract.FragmentManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
        = inflater.inflate(layoutId, container, false)

}