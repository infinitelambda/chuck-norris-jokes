package com.infinitelambda.chuck.util

import kotlinx.coroutines.*
import platform.Foundation.NSError
import platform.Foundation.NSLocalizedDescriptionKey
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal
import kotlin.native.concurrent.freeze

@ThreadLocal
internal object PlatformDispatcher {

    private var injectedMain: CoroutineDispatcher? = null

    private var injectedDefault: CoroutineDispatcher? = null

    val Main = injectedMain ?: Dispatchers.Main

    val Default = injectedDefault ?: Dispatchers.Default

    fun injectMainDispatcher(dispatcher: CoroutineDispatcher) {
        injectedMain = dispatcher
    }

    fun injectDefaultDispatcher(dispatcher: CoroutineDispatcher) {
        injectedDefault = dispatcher
    }

    fun resetDefault() {
        injectedMain = null
        injectedDefault = null
    }
}

@SharedImmutable
internal val iosScope = CoroutineScope(PlatformDispatcher.Main + SupervisorJob())

typealias CompletionHandler<T> = (T?, NSError?) -> Unit

interface NativeCancellable {

    fun cancel()

}

internal fun <T> CoroutineScope.withNativeCompletionHandler(
    completionHandler: CompletionHandler<T>,
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend () -> Result<T>
): NativeCancellable {
    val job = launch(context) {
        withContext(PlatformDispatcher.Default) { block() }
            .fold(
                { completionHandler(it.freeze(), null) },
                { completionHandler(null, it.asNSError().freeze()) }
            )
    }

    job.invokeOnCompletion {
        if ((it == null) || (it is CancellationException)) {
            return@invokeOnCompletion
        }

        completionHandler(null, it.asNSError().freeze())
    }

    return object : NativeCancellable {
        override fun cancel() {
            job.cancel()
        }
    }.freeze()
}

internal fun Throwable.asNSError(): NSError =
    NSError.errorWithDomain(
        domain = "KotlinException",
        code = 0,
        userInfo = mapOf<Any?, Any?>(
            "KotlinException" to this.freeze(),
            Pair(NSLocalizedDescriptionKey, message ?: "An unexpected error occurred")
        )
    )