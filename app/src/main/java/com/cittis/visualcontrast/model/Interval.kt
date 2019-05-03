package com.cittis.visualcontrast.model

import android.os.Parcel
import android.os.Parcelable

data class Interval (
    var idInterval: Int,
    var initTime:String,
    var endTime:String,

    // Array Vehicles
    var listVehicles: ArrayList<Vehicle>

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
        source.readString(),
        source.readString(),
        arrayListOf<Vehicle>().apply {
            source.readArrayList(Vehicle::class.java.classLoader)
        }
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            idInterval?.let { it1 -> dest.writeInt(it1) }
            dest.readString()
            dest.readString()
        }
        var size: Int? = listVehicles?.size
        if (size == null) {
            size = 0
        }
        for (i in 0 until size) {
            dest?.writeParcelableArray(arrayOf(listVehicles?.get(i)), flags)
        }
    }

    override fun describeContents(): Int = 0


}
