package zlc.season.rxrouter

interface Firewall {
    fun allow(datagram: Datagram): Boolean

    fun thrown(): Throwable?
}