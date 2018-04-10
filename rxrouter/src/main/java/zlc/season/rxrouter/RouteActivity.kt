package zlc.season.rxrouter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import zlc.season.rxrouter.RxRouter.Companion.ROUTE_DATA

class RouteActivity : Activity() {
    lateinit var datagram: Datagram

    companion object {
        const val IN_DATAGRAM = "zlc.season.rxrouter.datagram"
        const val RC_ROUTE = 1001

        fun route(context: Context, datagram: Datagram) {
            val intent = Intent(context, RouteActivity::class.java)
            intent.putExtra(IN_DATAGRAM, datagram)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun route(activity: Activity, datagram: Datagram) {
            val intent = Intent(activity, RouteActivity::class.java)
            intent.putExtra(IN_DATAGRAM, datagram)
            activity.startActivity(intent)
        }

        fun route(fragment: Fragment, datagram: Datagram) {
            val intent = Intent(fragment.context, RouteActivity::class.java)
            intent.putExtra(IN_DATAGRAM, datagram)
            fragment.context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        datagram = intent.getParcelableExtra(IN_DATAGRAM)

        try {
            realRoute()
        } catch (throwable: Throwable) {
            RouteResultServiceHolder.get(datagram)?.error(throwable)
        }
    }

    private fun realRoute() {
        when {
            datagram.url != null -> routeUrl(datagram)
            datagram.clazz != null -> routeClass(datagram)
            datagram.action != null -> routeAction(datagram)
        }
    }

    private fun routeAction(datagram: Datagram) {
        val realIntent = Intent(datagram.action)
        if (datagram.uri != null && datagram.type != null) {
            realIntent.setDataAndType(datagram.uri, datagram.type)
        } else if (datagram.type != null) {
            realIntent.type = datagram.type
        } else if (datagram.uri != null) {
            realIntent.data = datagram.uri
        }

        datagram.flags?.let { realIntent.addFlags(it) }
        datagram.category?.let { realIntent.addCategory(it) }
        datagram.bundle?.let { realIntent.putExtras(it) }

        datagram.isSystemAction?.let {
            if (!it) {
                realIntent.putExtra(ROUTE_DATA, datagram)
            }
        }

        startActivityForResult(realIntent, RC_ROUTE)
    }

    private fun routeClass(datagram: Datagram) {
        val realIntent = Intent(RouteActivity@ this, datagram.clazz)
        datagram.flags?.let { realIntent.addFlags(it) }

        realIntent.putExtra(ROUTE_DATA, datagram)
        startActivityForResult(realIntent, RC_ROUTE)
    }

    private fun routeUrl(datagram: Datagram) {
        val dest = RxRouterProviders.provide(datagram.url)
                ?: throw IllegalStateException("This url [${datagram.url}] not found! " +
                        "Please confirm this route is added.")

        val realIntent = Intent(RouteActivity@ this, dest)
        datagram.flags?.let { realIntent.addFlags(it) }

        realIntent.putExtra(ROUTE_DATA, datagram)
        startActivityForResult(realIntent, RC_ROUTE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            RouteResultServiceHolder.get(datagram)?.success(Result.empty())
            finish()
            return
        }

        if (requestCode == RC_ROUTE) {
            RouteResultServiceHolder.get(datagram)?.success(Result(resultCode, data))
            finish()
            return
        }

        throw IllegalStateException("This should never happen.")
    }
}