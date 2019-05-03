package com.cittis.visualcontrast.model

import android.os.Parcel
import android.os.Parcelable

data class Aforo(
    var idAforo: Int,
    var address: String?,
    var date: String?,//Date
    // Forign
    var idCoordinates: Coordinates,
    var idInterval: Interval

) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Aforo> = object : Parcelable.Creator<Aforo> {
            override fun newArray(size: Int): Array<Aforo?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): Aforo = Aforo(source)
        }

    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readParcelable(Coordinates::class.java.classLoader),
        source.readParcelable(Interval::class.java.classLoader)

    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeInt(idAforo)
            dest.writeString(address)
            dest.writeString(address)
            dest.writeParcelable(idCoordinates, flags)
            dest.writeParcelable(idInterval, flags)

        }
    }

    override fun describeContents(): Int = 0
}