package com.cittis.visualcontrast.model

import android.os.Parcel
import android.os.Parcelable

data class EnterpriseVehicle(
    var idEnterpriseVehicle: Int,
    var nameTypeService: String
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<EnterpriseVehicle> = object : Parcelable.Creator<EnterpriseVehicle> {
            override fun newArray(size: Int): Array<EnterpriseVehicle?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): EnterpriseVehicle = EnterpriseVehicle(source)
        }
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            idEnterpriseVehicle?.let { it1 -> dest.writeInt(it1) }
            dest.readString()
        }
    }

    override fun describeContents(): Int = 0


}