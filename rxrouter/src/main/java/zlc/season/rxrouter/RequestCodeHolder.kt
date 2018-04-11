package zlc.season.rxrouter

object RequestCodeHolder {
    private val MAP: MutableMap<Int, Datagram> = mutableMapOf()

    @Synchronized
    fun put(requestCode: Int, datagram: Datagram) {
        MAP[requestCode] = datagram
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