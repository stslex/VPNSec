package com.stslex.home_feature.di

import com.stslex.home_feature.ui.FeatureHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class ModuleFeatureHome {

    val module = module {
        viewModelOf(::FeatureHomeViewModel)
    }
}