package zlc.season.rxrouter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import io.reactivex.Maybe

class LocalRouter : Router {
    override fun route(context: Context, datagram: Datagram): Maybe<Result> {
        return real(datagram, {
            RouteActivity.route(context, datagram)
        })
    }

    override fun route(activity: FragmentActivity, datagram: Datagram): Maybe<Result> {
        return real(datagram, {
            RouteFragment.route(activity, datagram)
        })
    }

    override fun route(fragment: Fragment, datagram: Datagram): Maybe<Result> {
        return real(datagram, {
            RouteFragment.route(fragment, datagram)
        })
    }

    private fun real(datagram: Datagram, action: () -> Unit): Maybe<Result> {
        return Maybe.just(obtain(datagram))
                .doOnSuccess {
                    action()
                }
                .flatMap {
                    it.get().lastElement()
                }
    }

    private fun obtain(datagram: Datagram): RouteResultService {
        val routeService = RouteResultService()
        RouteResultServiceHolder.put(datagram, routeService)
        return routeService
    }
}