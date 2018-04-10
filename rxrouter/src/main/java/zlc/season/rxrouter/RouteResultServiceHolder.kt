package zlc.season.rxrouter

object RouteResultServiceHolder {
    private val MAP: MutableMap<Datagram, RouteResultService> = mutableMapOf()

    @Synchronized
    fun put(datagram: Datagram, resultService: RouteResultService) {
        MAP[datagram] = resultService
    }

    @Synchronized
    fun get(datagram: Datagram): RouteResultService? {
        return MAP[datagram]
    }

    @Synchronized
    fun remove(datagram: Datagram) {
        MAP.remove(datagram)
    }
}