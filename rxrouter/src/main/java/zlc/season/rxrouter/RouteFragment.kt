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
        const val RC_ROUTE = 1001

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

    private lateinit var datagram: Datagram

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            realRoute(it.getParcelable(IN_DATAGRAM))
        }
    }

    private fun realRoute(datagram: Datagram) {
        this.datagram = datagram

        val dest = RxRouterProviders.provide(datagram.uri)
                ?: throw IllegalStateException("This uri [${datagram.uri}] not found! Please confirm this route is added.")
        val realIntent = Intent(context, dest)
        realIntent.putExtra(ROUTE_DATA, datagram)
        startActivityForResult(realIntent, RC_ROUTE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        if (requestCode == RC_ROUTE) {
            RouteResultServiceHolder.get(datagram.uri)?.success(Result(resultCode, data))
            return
        }

        throw IllegalStateException("This should never happen.")
    }
}

