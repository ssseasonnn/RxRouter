package zlc.season.rxrouter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import io.reactivex.Maybe

class LocalRouter : Router {
    override fun route(context: Context, datagram: Datagram, firewalls: List<Firewall>): Maybe<Result> {
        return real(datagram, firewalls) {
            RouteActivity.route(context, datagram)
        }
    }

    override fun route(activity: FragmentActivity, datagram: Datagram, firewalls: List<Firewall>): Maybe<Result> {
        return real(datagram, firewalls) {
            RouteFragment.route(activity, datagram)
        }
    }

    override fun route(fragment: Fragment, datagram: Datagram, firewalls: List<Firewall>): Maybe<Result> {
        return real(datagram, firewalls) {
            RouteFragment.route(fragment, datagram)
        }
    }

    private fun real(datagram: Datagram, firewalls: List<Firewall>, action: () -> Unit): Maybe<Result> {
        return Maybe.just(firewalls)
                .doOnSuccess {
                    for (firewall in firewalls) {
                        if (!firewall.allow(datagram)) {
                            firewall.thrown()?.let { throw  it }
                            throw Exceptions.FirewallDenied()
                        }
                    }
                }
                .map {
                    obtain(datagram)
                }
                .doOnSuccess {
                    action()
                }
                .flatMap {
                    it.get().lastElement()
                }
                .doFinally {
                    RouteResultServiceHolder.remove(datagram)
                }
    }

    private fun obtain(datagram: Datagram): RouteResultService {
        val routeService = RouteResultService()
        RouteResultServiceHolder.put(datagram, routeService)
        return routeService
    }
}