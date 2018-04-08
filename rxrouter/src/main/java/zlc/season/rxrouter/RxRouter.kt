package zlc.season.rxrouter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import io.reactivex.Maybe

class RxRouter private constructor() {
    private var router: Router = LocalRouter()

    private var datagram: Datagram = Datagram.empty()

    private var context: Context? = null
    private var activity: Activity? = null
    private var fragment: Fragment? = null

    companion object {
        fun of(context: Context): RxRouter {
            val rxRouter = RxRouter()
            rxRouter.context = context
            return rxRouter
        }

        fun of(activity: Activity): RxRouter {
            val rxRouter = RxRouter()
            rxRouter.activity = activity
            return rxRouter
        }

        fun of(fragment: Fragment): RxRouter {
            val rxRouter = RxRouter()
            rxRouter.fragment = fragment
            return rxRouter
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
        this.datagram.data = bundle
        return this
    }

    fun route(uri: String): Maybe<Result> {
        datagram.uri = uri
        return when {
            context != null -> router.route(context!!, datagram)
            activity != null -> router.route(activity!!, datagram)
            fragment != null -> router.route(fragment!!, datagram)
            else -> throw IllegalStateException("This will never happen.")
        }
    }
}