package com.cittis.visualcontrast.model

import android.os.Parcel
import android.os.Parcelable

data class Coordinates(
    var idCoordinates: Int,
    var altitude: Float?,
    var longitude: Float?,
    var latitude: Float?
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Coordinates> = object : Parcelable.Creator<Coordinates> {
            override fun newArray(size: Int): Array<Coordinates?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): Coordinates = Coordinates(source)
        }

    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readFloat(),
        source.readFloat(),
        source.readFloat()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            idCoordinates?.let { it1 -> dest.writeInt(it1) }
            altitude?.let { it1 -> dest.writeFloat(it1) }
            longitude?.let { it1 -> dest.writeFloat(it1) }
            altitude?.let { it1 -> dest.writeFloat(it1) }
        }
    }

    override fun describeContents(): Int = 0

    override fun toString(): String {
        return "\"Coordinates\":{\"idCoordinates\":$idCoordinates, \"altitude\":$altitude, \"longitude\":$longitude, \"latitude\":$latitude}"
    }


}