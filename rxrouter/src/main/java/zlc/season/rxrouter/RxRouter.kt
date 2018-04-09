package zlc.season.rxrouter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import io.reactivex.Maybe

class RxRouter private constructor() {
    private var router: Router = LocalRouter()

    private var datagram: Datagram = Datagram.empty()

    private var context: Context? = null
    private var activity: FragmentActivity? = null
    private var fragment: Fragment? = null

    companion object {
        const val ROUTE_DATA = "zlc.season.rxrouter.route_data"

        fun of(context: Context): RxRouter {
            val rxRouter = RxRouter()
            rxRouter.context = context
            return rxRouter
        }

        fun of(activity: FragmentActivity): RxRouter {
            val rxRouter = RxRouter()
            rxRouter.activity = activity
            return rxRouter
        }

        fun of(fragment: Fragment): RxRouter {
            val rxRouter = RxRouter()
            rxRouter.fragment = fragment
            return rxRouter
        }

        fun data(intent: Intent): Maybe<Datagram> {
            val datagram = intent.getParcelableExtra<Datagram>(ROUTE_DATA)
            return Maybe.just(datagram)
        }
    }

    fun with(intValue: Int): RxRouter {
        this.datagram.intValue = intValue
        return this
    }

    fun with(longValue: Long): RxRouter {
        this.datagram.longValue = longValue
        return this
    }

    fun with(floatValue: Float): RxRouter {
        this.datagram.floatValue = floatValue
        return this
    }

    fun with(doubleValue: Double): RxRouter {
        this.datagram.doubleValue = doubleValue
        return this
    }

    fun with(stringValue: String): RxRouter {
        this.datagram.stringValue = stringValue
        return this
    }

    fun with(booleanValue: Boolean): RxRouter {
        this.datagram.booleanValue = booleanValue
        return this
    }

    fun with(bundle: Bundle): RxRouter {
        this.datagram.bundle = bundle
        return this
    }

    fun addFlags(flags: Int): RxRouter {
        this.datagram.flags = flags
        return this
    }

    fun addCategory(category: String): RxRouter {
        this.datagram.category = category
        return this
    }

    fun route(uri: String): Maybe<Result> {
        datagram.uri = uri
        return dispatch()
    }

    fun routeAction(action: String): Maybe<Result> {
        datagram.action = action
        return dispatch()
    }

    fun routeClass(clazz: Class<*>): Maybe<Result> {
        datagram.clazz = clazz
        return dispatch()
    }

    private fun dispatch(): Maybe<Result> {
        return when {
            activity != null -> router.route(activity!!, datagram)
            fragment != null -> router.route(fragment!!, datagram)
            context != null -> router.route(context!!, datagram)
            else -> throw IllegalStateException("This should never happen.")
        }
    }
}