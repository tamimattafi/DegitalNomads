package com.tamimattafi.degitalnomads.view.global

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.tamimattafi.degitalnomads.R

abstract class NavigationActivity : AppCompatActivity(), NavigationContract.FragmentManager {

    abstract val layoutId : Int
    abstract fun onViewCreated(savedInstanceState: Bundle?)

    protected val currentFragment : Fragment?
    get() = supportFragmentManager.findFragmentById(rootView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(R.style.AppTheme, true)
        setContentView(layoutId)
        onViewCreated(savedInstanceState)
    }

    override fun requestAttachBaseScreen(fragment: Fragment) {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.inTransaction {
            replace(rootView, fragment)
        }
    }


    override fun requestSlideLeftScreen(fragment : Fragment, name : String) {
        supportFragmentManager.inTransaction {
            setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
            add(rootView, fragment).addToBackStack(name)
        }
    }

    override fun onBackPressed() {
        (currentFragment as? NavigationContract.BackPressController)?.let {
            if (it.onBackPressed()) {
                super.onBackPressed()
            }
        } ?: super.onBackPressed()
    }

    override fun requestBackPress() {
        onBackPressed()
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    override fun startActivityForResult(intent: Intent) {
        startActivityForResult(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        (currentFragment as? NavigationContract.ActivityResultReceiver)?.onActivityResult(requestCode, resultCode, data)
    }
}