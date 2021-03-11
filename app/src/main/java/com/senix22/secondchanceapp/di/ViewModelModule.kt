package com.senix22.secondchanceapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.senix22.secondchanceapp.ui.userInfo.UserInfoViewModel
import com.senix22.secondchanceapp.ui.repositoryDetails.pulls.PullsListViewModel
import com.senix22.secondchanceapp.utils.viewmodel.ViewModelFactory
import com.senix22.secondchanceapp.utils.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    abstract fun userInfoViewModel(viewModel: UserInfoViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(PullsListViewModel::class)
    abstract fun pullsViewModel(viewModel: PullsListViewModel): ViewModel

}