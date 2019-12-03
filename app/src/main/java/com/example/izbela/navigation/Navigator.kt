package com.example.izbela.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.izbela.R
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class Navigator(
    fragmentActivity: FragmentActivity,
    fragmentManager: FragmentManager,
    container: Int
) : SupportAppNavigator(fragmentActivity, fragmentManager, container) {

    override fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction?
    ) {
        fragmentTransaction?.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction)
    }
}