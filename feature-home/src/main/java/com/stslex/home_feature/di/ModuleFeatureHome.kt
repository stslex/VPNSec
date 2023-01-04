package com.stslex.home_feature.di

import com.stslex.home_feature.data.DataStore.vpnDataStore
import com.stslex.home_feature.data.repository.VpnConfigRepository
import com.stslex.home_feature.data.repository.VpnConfigRepositoryImpl
import com.stslex.home_feature.ui.FeatureHomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class ModuleFeatureHome {

    val module = module {

        viewModelOf(::FeatureHomeViewModel)

        single<VpnConfigRepository> {
            VpnConfigRepositoryImpl(
                dataStore = androidContext().vpnDataStore
            )
        }
    }
}