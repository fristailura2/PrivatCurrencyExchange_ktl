package com.example.ura.myapplication.di

import com.example.ura.myapplication.di.annotaion.ActivityScope
import com.example.ura.myapplication.di.annotaion.FragmentScope
import com.example.ura.myapplication.presentation.graph.GraphActivity
import com.example.ura.myapplication.presentation.graph.GraphView
import com.example.ura.myapplication.presentation.main.MainActivity
import com.example.ura.myapplication.presentation.main.MainView
import com.example.ura.myapplication.presentation.main.nbu.NBUFragment
import com.example.ura.myapplication.presentation.main.nbu.NBUView
import com.example.ura.myapplication.presentation.main.privat.PrivatFragment
import com.example.ura.myapplication.presentation.main.privat.PrivateView

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AndroidBindingModule {
    @ActivityScope
    @ContributesAndroidInjector
    fun mainActivity(): MainActivity

    @FragmentScope
    @ContributesAndroidInjector
    fun nbuFragment(): NBUFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun privatFragment(): PrivatFragment

    @ActivityScope
    @ContributesAndroidInjector
    fun graphActivity(): GraphActivity

    @ActivityScope
    @Binds
    fun getMainView(settingsActivity: MainActivity): MainView

    @FragmentScope
    @Binds
    fun getNBUView(nbuFragment: NBUFragment): NBUView

    @FragmentScope
    @Binds
    fun getPrivateView(privatFragment: PrivatFragment): PrivateView

    @ActivityScope
    @Binds
    fun getGraphView(settingsActivity: GraphActivity): GraphView

}