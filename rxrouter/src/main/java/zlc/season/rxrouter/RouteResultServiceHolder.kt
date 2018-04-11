package zlc.season.rxrouter

object RouteResultServiceHolder {
    private val MAP: MutableMap<Long, RouteResultService> = mutableMapOf()

    @Synchronized
    fun put(datagram: Datagram, resultService: RouteResultService) {
        MAP[datagram.magicNumber] = resultService
    }

    @Synchronized
    fun get(datagram: Datagram): RouteResultService? {
        return MAP[datagram.magicNumber]
    }

    @Synchronized
    fun remove(datagram: Datagram) {
        MAP.remove(datagram.magicNumber)
    }
}