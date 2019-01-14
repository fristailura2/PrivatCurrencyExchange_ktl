package com.example.ura.myapplication.di

import com.example.ura.myapplication.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ModulsModule::class,
        AndroidSupportInjectionModule::class,
        AndroidBindingModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): ApplicationComponent.Builder

        fun build(): ApplicationComponent
    }


}
