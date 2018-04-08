package zlc.season.rxrouter

import zlc.season.rxrouterannotation.Provider

object RxRouterProviders : Provider {
    private val routerProviders: MutableList<Provider> = mutableListOf()

    override fun provide(uri: String?): Class<*>? {
        for (item in routerProviders) {
            if (item.provide(uri) != null) {
                return item.provide(uri)
            }
        }
        return null
    }

    fun add(provider: Provider) {
        routerProviders.add(provider)
    }

    fun add(vararg provider: Provider) {
        routerProviders.addAll(provider)
    }
}