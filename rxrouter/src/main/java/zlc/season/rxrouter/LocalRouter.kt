package zlc.season.rxrouter

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import io.reactivex.Maybe

class LocalRouter : Router {
    override fun route(context: Context, datagram: Datagram): Maybe<Result> {
        return real(datagram, {
            RouteActivity.route(context, datagram)
        })
    }

    override fun route(activity: Activity, datagram: Datagram): Maybe<Result> {
        return real(datagram, {
            RouteActivity.route(activity, datagram)
        })
    }

    override fun route(fragment: Fragment, datagram: Datagram): Maybe<Result> {
        return real(datagram, {
            RouteActivity.route(fragment, datagram)
        })
    }

    private fun real(datagram: Datagram, action: () -> Unit): Maybe<Result> {
        var routeService = RouteResultServiceHolder.get(datagram.uri)
        if (routeService == null) {
            routeService = RouteResultService()
            RouteResultServiceHolder.put(datagram.uri, routeService)
        }

        action()

        return routeService.get().lastElement()
    }
}