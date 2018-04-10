package zlc.season.rxrouter

import zlc.season.rxrouterannotation.Provider

object RxRouterProviders : Provider {
    private val routerProviders: MutableList<Provider> = mutableListOf()

    override fun provide(url: String?): Class<*>? {
        for (item in routerProviders) {
            if (item.provide(url) != null) {
                return item.provide(url)
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