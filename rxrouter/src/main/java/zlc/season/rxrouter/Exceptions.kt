package zlc.season.rxrouter

interface Exceptions {
    class FirewallDenied : RuntimeException("This operation was denied by the firewall!")
}