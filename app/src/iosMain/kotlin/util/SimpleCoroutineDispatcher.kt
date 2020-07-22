package util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext
import platform.darwin.*

/*
* Simple custom CoroutineDispatcher for iOS
* since there is no default one yet:
* https://github.com/Kotlin/kotlinx.coroutines/issues/470
* */

class SimpleCoroutineDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
}

