package com.senix22.secondchanceapp.di

import com.senix22.secondchanceapp.ui.userInfo.UserInfoFragment
import com.senix22.secondchanceapp.ui.NavigationActivity
import com.senix22.secondchanceapp.ui.repositoryDetails.pulls.PullsListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: NavigationActivity)
    fun inject(fragment: UserInfoFragment)
    fun inject(fragment: PullsListFragment)
}