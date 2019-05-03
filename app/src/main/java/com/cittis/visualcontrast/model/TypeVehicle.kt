package com.cittis.visualcontrast.model

import android.os.Parcel
import android.os.Parcelable

data class TypeVehicle(
    var idTypeVehicle: Int,
    var nameTypeVehicle: String
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TypeVehicle> = object : Parcelable.Creator<TypeVehicle> {
            override fun newArray(size: Int): Array<TypeVehicle?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): TypeVehicle = TypeVehicle(source)
        }
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            idTypeVehicle?.let { it1 -> dest.writeInt(it1) }
            dest.readString()
        }
    }

    override fun describeContents(): Int = 0


}