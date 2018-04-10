package zlc.season.rxrouter

import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

class Datagram(
        var url: String? = null,
        var action: String? = null,
        var uri: Uri? = null,
        var type: String? = null,
        var flags: Int? = null,
        var category: String? = null,
        var clazz: Class<*>? = null,
        var intValue: Int? = null,
        var longValue: Long? = null,
        var floatValue: Float? = null,
        var doubleValue: Double? = null,
        var stringValue: String? = null,
        var booleanValue: Boolean? = null,
        var bundle: Bundle? = null,
        var isSystemAction: Boolean? = null

) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readParcelable<Uri>(Uri::class.java.classLoader),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readSerializable() as Class<*>?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readValue(Float::class.java.classLoader) as Float?,
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readParcelable<Bundle>(Bundle::class.java.classLoader),
            source.readValue(Boolean::class.java.classLoader) as Boolean?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(url)
        writeString(action)
        writeParcelable(uri, 0)
        writeString(type)
        writeValue(flags)
        writeString(category)
        writeSerializable(clazz)
        writeValue(intValue)
        writeValue(longValue)
        writeValue(floatValue)
        writeValue(doubleValue)
        writeString(stringValue)
        writeValue(booleanValue)
        writeParcelable(bundle, 0)
        writeValue(isSystemAction)
    }

    companion object {
        fun empty(): Datagram {
            return Datagram()
        }

        @JvmField
        val CREATOR: Parcelable.Creator<Datagram> = object : Parcelable.Creator<Datagram> {
            override fun createFromParcel(source: Parcel): Datagram = Datagram(source)
            override fun newArray(size: Int): Array<Datagram?> = arrayOfNulls(size)
        }
    }
}