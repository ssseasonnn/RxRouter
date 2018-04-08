package zlc.season.rxrouter

object RouteResultServiceHolder {
    private val MAP: MutableMap<String, RouteResultService> = mutableMapOf()

    fun put(uri: String, resultService: RouteResultService) {
        MAP[uri] = resultService
    }

    fun get(uri: String): RouteResultService? {
        return MAP[uri]
    }
}