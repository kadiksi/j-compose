package kz.jcourier.location.sync


import android.os.SystemClock

interface Wait {
    /**
     * @throws WaitingTimeoutException
     * */
    suspend fun waitWhile(timeout: Long, condition: () -> Boolean)
}

class CoroutinesWait : Wait {

    override suspend fun waitWhile(timeout: Long, condition: () -> Boolean) {
        val startedTime = SystemClock.elapsedRealtime()

        while (condition.invoke()) {
            if (SystemClock.elapsedRealtime() - startedTime > timeout) {
                throw WaitingTimeoutException()
            }
        }
    }
}


class WaitingTimeoutException(message: String? = null) : Exception(message)