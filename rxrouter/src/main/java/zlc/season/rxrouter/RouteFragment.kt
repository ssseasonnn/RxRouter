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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            realRoute(it.getParcelable(IN_DATAGRAM))
        }
    }

    private fun realRoute(datagram: Datagram) {
        val requestCode = RequestCodeHolder.put(datagram)

        try {
            when {
                datagram.url != null -> routeUrl(datagram, requestCode)
                datagram.clazz != null -> routeClass(datagram, requestCode)
                datagram.action != null -> routeAction(datagram, requestCode)
            }
        } catch (throwable: Throwable) {
            RouteResultServiceHolder.get(datagram)?.error(throwable)
        }
    }

    private fun routeAction(datagram: Datagram, requestCode: Int) {
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

        startActivityForResult(realIntent, requestCode)
    }

    private fun routeClass(datagram: Datagram, requestCode: Int) {
        val realIntent = Intent(context, datagram.clazz)

        datagram.flags?.let { realIntent.addFlags(it) }

        realIntent.putExtra(ROUTE_DATA, datagram)
        startActivityForResult(realIntent, requestCode)
    }

    private fun routeUrl(datagram: Datagram, requestCode: Int) {
        val dest = RxRouterProviders.provide(datagram.url)
                ?: throw IllegalStateException("This url [${datagram.url}] not found! " +
                        "Please confirm this route is added.")

        val realIntent = Intent(context, dest)
        datagram.flags?.let { realIntent.addFlags(it) }

        realIntent.putExtra(ROUTE_DATA, datagram)
        startActivityForResult(realIntent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val datagram = RequestCodeHolder.get(requestCode)
                ?: throw IllegalStateException("This should never happen.")

        if (data == null) {
            RouteResultServiceHolder.get(datagram)?.success(Result.empty())
            RequestCodeHolder.remove(requestCode)
            return
        }

        RouteResultServiceHolder.get(datagram)?.success(Result(resultCode, data))
        RequestCodeHolder.remove(requestCode)
    }
}

