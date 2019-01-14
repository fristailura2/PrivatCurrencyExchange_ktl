package com.example.ura.myapplication.di



import com.example.ura.myapplication.data.exchange.ExchengeRepositoryModule
import com.example.ura.myapplication.data.exchange.api.ApiModule
import dagger.Module

@Module(includes = [ExchengeRepositoryModule::class, ApiModule::class, AppContextModule::class])
interface ModulsModule
