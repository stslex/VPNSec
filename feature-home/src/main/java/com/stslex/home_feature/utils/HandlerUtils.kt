package com.stslex.home_feature.utils

import android.util.Log
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.core.component.getScopeName

object HandlerUtils {

    val <T> T.corruptionHandler: ReplaceFileCorruptionHandler<T>
        get() = ReplaceFileCorruptionHandler { exception ->
            logException("ReplaceFileCorruptionHandler", exception)
            this
        }

    val coroutineHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        logException(coroutineContext.getScopeName().value, throwable)
    }

    fun logException(tag: String, throwable: Throwable) {
        Log.e(tag, throwable.message, throwable.cause)
    }
}