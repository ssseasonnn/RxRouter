package zlc.season.rxrouter

import android.os.Parcelable

interface Firewall : Parcelable {
    fun allow(datagram: Datagram): Boolean
}