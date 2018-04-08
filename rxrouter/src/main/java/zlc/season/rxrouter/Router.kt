package zlc.season.rxrouter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import io.reactivex.Maybe

interface Router {
    fun route(context: Context, datagram: Datagram): Maybe<Result>

    fun route(activity: FragmentActivity, datagram: Datagram): Maybe<Result>

    fun route(fragment: Fragment, datagram: Datagram): Maybe<Result>
}