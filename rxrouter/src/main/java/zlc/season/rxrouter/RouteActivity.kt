package zlc.season.rxrouter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

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

        val realIntent = Intent(RouteActivity@ this, RxRouterProviders.provide(datagram.uri))

        if (datagram.data != null) {
            realIntent.putExtras(datagram.data)
        }

        startActivityForResult(realIntent, RC_ROUTE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            finish()
            return
        }

        if (requestCode == RC_ROUTE) {
            RouteResultServiceHolder.get(datagram.uri)?.dispatch(Result(resultCode, data!!))
            finish()
        }

        throw IllegalStateException("This will never happen.")
    }
}