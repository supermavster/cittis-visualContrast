package com.cittis.visualcontrast.model


import android.os.Parcel
import android.os.Parcelable

data class EnterpriseVehiclesMunicipalities(
    var idMunicipality: Int,
    var listEnterpriseVehicle: EnterpriseVehicle
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<EnterpriseVehiclesMunicipalities> = object : Parcelable.Creator<EnterpriseVehiclesMunicipalities> {
            override fun newArray(size: Int): Array<EnterpriseVehiclesMunicipalities?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): EnterpriseVehiclesMunicipalities = EnterpriseVehiclesMunicipalities(source)
        }
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readParcelable(EnterpriseVehicle::class.java.classLoader)
        )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            idMunicipality?.let { it1 -> dest.writeInt(it1) }
            dest.writeParcelable(listEnterpriseVehicle, flags)
        }
    }

    override fun describeContents(): Int = 0
    
}