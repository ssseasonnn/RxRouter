package zlc.season.rxrouter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import io.reactivex.Maybe

interface Router {
    fun route(context: Context, datagram: Datagram, firewalls: List<Firewall>): Maybe<Result>

    fun route(activity: FragmentActivity, datagram: Datagram, firewalls: List<Firewall>): Maybe<Result>

    fun route(fragment: Fragment, datagram: Datagram, firewalls: List<Firewall>): Maybe<Result>
}