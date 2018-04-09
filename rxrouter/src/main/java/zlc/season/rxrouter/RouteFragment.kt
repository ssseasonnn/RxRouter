package zlc.season.rxrouter


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import zlc.season.rxrouter.RxRouter.Companion.ROUTE_DATA

class RouteFragment : Fragment() {

    init {
        retainInstance = true
    }

    companion object {
        private const val ROUTE_TAG = "zlc.season.rxrouter.tag"
        const val IN_DATAGRAM = "zlc.season.rxrouter.datagram"

        fun route(activity: FragmentActivity, datagram: Datagram) {
            val fm = activity.supportFragmentManager
            route(fm, datagram)
        }

        fun route(fragment: Fragment, datagram: Datagram) {
            val fm = fragment.childFragmentManager
            route(fm, datagram)
        }

        private fun route(fm: FragmentManager, datagram: Datagram) {
            val routeFragment = findFragment(fm)
            if (routeFragment != null) {
                routeFragment.realRoute(datagram)
            } else {
                createFragment(fm, datagram)
            }
        }

        private fun findFragment(fm: FragmentManager): RouteFragment? {
            if (fm.isDestroyed) {
                throw IllegalStateException("Fragment Manager has been destroyed")
            }

            val fragment = fm.findFragmentByTag(ROUTE_TAG)
            return fragment as RouteFragment?
        }

        private fun createFragment(fm: FragmentManager, datagram: Datagram): RouteFragment {
            val fragment = RouteFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IN_DATAGRAM, datagram)
                }
            }
            fm.beginTransaction().add(fragment, ROUTE_TAG).commitAllowingStateLoss()
            return fragment
        }
    }

    private var requestCode = 101
    private val map: MutableMap<Int, Datagram> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val datagram = arguments.getParcelable<Datagram>(IN_DATAGRAM)

        try {
            realRoute(datagram)
        } catch (throwable: Throwable) {
            RouteResultServiceHolder.get(datagram)?.error(throwable)
        }
    }

    @Synchronized
    private fun realRoute(datagram: Datagram) {
        map[requestCode] = datagram

        when {
            datagram.uri != null -> routeUri(datagram, requestCode)
            datagram.clazz != null -> routeClass(datagram, requestCode)
            datagram.action != null -> routeAction(datagram, requestCode)
        }

        requestCode++
    }

    private fun routeAction(datagram: Datagram, requestCode: Int) {
        val realIntent = Intent(datagram.action)
        realIntent.putExtra(ROUTE_DATA, datagram)
        startActivityForResult(realIntent, requestCode)
    }

    private fun routeClass(datagram: Datagram, requestCode: Int) {
        val realIntent = Intent(context, datagram.clazz)
        realIntent.putExtra(ROUTE_DATA, datagram)
        startActivityForResult(realIntent, requestCode)
    }

    private fun routeUri(datagram: Datagram, requestCode: Int) {
        val dest = RxRouterProviders.provide(datagram.uri)
                ?: throw IllegalStateException("This uri [${datagram.uri}] not found! " +
                        "Please confirm this route is added.")

        val realIntent = Intent(context, dest)
        realIntent.putExtra(ROUTE_DATA, datagram)
        startActivityForResult(realIntent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val datagram = map[requestCode] ?: throw IllegalStateException("This should never happen.")

        if (data == null) {
            RouteResultServiceHolder.get(datagram)?.success(Result.empty())
            return
        }

        RouteResultServiceHolder.get(datagram)?.success(Result(resultCode, data))
    }
}

