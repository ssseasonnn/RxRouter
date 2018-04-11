package zlc.season.rxrouter

object RequestCodeHolder {
    private var REQUEST_CODE = 101
    private val MAP: MutableMap<Int, Datagram> = mutableMapOf()

    @Synchronized
    fun put(datagram: Datagram): Int {
        val requestCode = REQUEST_CODE
        MAP[requestCode] = datagram
        REQUEST_CODE++
        return requestCode
    }

    @Synchronized
    fun get(requestCode: Int): Datagram? {
        return MAP[requestCode]
    }

    @Synchronized
    fun remove(requestCode: Int) {
        MAP.remove(requestCode)
    }
}