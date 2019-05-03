package com.cittis.visualcontrast.model

import android.os.Parcel
import android.os.Parcelable

data class Vehicle (
    var idVehicle: Int,
    var plaque:String,
    var numberPerson:Int,
    var inService:Int,//Boolean,
    // Extra
    var typeService: TypeService,
    var typeVehicle: TypeVehicle,
    // Array Vehicles
    var enterpriseVehicle: EnterpriseVehicle

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
        source.readInt(),
        source.readInt(),
        source.readParcelable(TypeService::class.java.classLoader),
        source.readParcelable(TypeVehicle::class.java.classLoader),
        source.readParcelable(EnterpriseVehicle::class.java.classLoader)
        )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            idVehicle?.let { it1 -> dest.writeInt(it1) }
            dest.readString()
            numberPerson?.let { it1 -> dest.writeInt(it1) }
            inService?.let { it1 -> dest.writeInt(it1) }
            dest.writeParcelable(typeService, flags)
            dest.writeParcelable(typeVehicle, flags)
            dest.writeParcelable(enterpriseVehicle, flags)
        }
    }

    override fun describeContents(): Int = 0


}