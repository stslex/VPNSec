package com.stslex.vpnsec

import android.app.Application
import com.stslex.vpnsec.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VPNSecApp : Application() {

    override fun onCreate() {
        startKoin {
            androidLogger()
            androidContext(this@VPNSecApp)
            modules(appModules)
        }
        super.onCreate()
    }
}