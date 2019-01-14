package com.example.ura.myapplication.di

import android.content.Context
import com.example.ura.myapplication.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppContextModule {
    @Provides
    @Singleton
    fun provideAppContext(app: App): Context {
        return app.applicationContext
    }
}
