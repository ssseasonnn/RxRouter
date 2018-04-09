package zlc.season.rxrouter

object RouteResultServiceHolder {
    private val MAP: MutableMap<String, RouteResultService> = mutableMapOf()

    fun put(datagram: Datagram, resultService: RouteResultService) {
        when {
            datagram.uri != null -> MAP[datagram.uri!!] = resultService
            datagram.clazz != null -> MAP[datagram.clazz!!.canonicalName] = resultService
            datagram.action != null -> MAP[datagram.action!!] = resultService
            else -> throw IllegalStateException("This should never happen!")
        }
    }

    fun get(datagram: Datagram): RouteResultService? {
        return when {
            datagram.uri != null -> MAP[datagram.uri!!]
            datagram.clazz != null -> MAP[datagram.clazz!!.canonicalName]
            datagram.action != null -> MAP[datagram.action!!]
            else -> throw IllegalStateException("This should never happen!")
        }
    }
}