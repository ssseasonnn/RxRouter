package zlc.season.rxrouter

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import io.reactivex.Maybe

interface Router {
    fun route(context: Context, datagram: Datagram): Maybe<Result>

    fun route(activity: Activity, datagram: Datagram): Maybe<Result>

    fun route(fragment: Fragment, datagram: Datagram): Maybe<Result>
}