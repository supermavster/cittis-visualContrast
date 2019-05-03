package com.cittis.visualcontrast.model


import android.os.Parcel
import android.os.Parcelable

data class CittisList(
    val idProject: Int,
    var dataUser: DataUser,
    var listAforo: ArrayList<Aforo>?,
    var lisEnterpriseVehiclesMunicipalities: ArrayList<EnterpriseVehiclesMunicipalities>?
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CittisList> = object : Parcelable.Creator<CittisList> {
            override fun newArray(size: Int): Array<CittisList?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): CittisList = CittisList(source)
        }
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readParcelable<DataUser>(DataUser::class.java.classLoader),
                arrayListOf<Aforo>().apply {
            source.readArrayList(Aforo::class.java.classLoader)
        },
        arrayListOf<EnterpriseVehiclesMunicipalities>().apply {
            source.readArrayList(EnterpriseVehiclesMunicipalities::class.java.classLoader)
        }
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeInt(idProject)
            dest.writeParcelable(dataUser, flags)
        }
        var size: Int? = listAforo?.size
        if (size == null) {
            size = 0
        }
        for (i in 0 until size) {
            dest?.writeParcelableArray(arrayOf(listAforo?.get(i)), flags)
        }
        size = lisEnterpriseVehiclesMunicipalities?.size
        if (size == null) {
            size = 0
        }
        for (i in 0 until size) {
            dest?.writeParcelableArray(arrayOf(lisEnterpriseVehiclesMunicipalities?.get(i)), flags)
        }
    }

    override fun describeContents(): Int = 0

    override fun toString(): String {
        return "[{\"CittisList\":{\"idProject\":\"$idProject\",\"dataUser\":$dataUser,\"listAforo\":$listAforo,\"lisEnterpriseVehiclesMunicipalities\":$lisEnterpriseVehiclesMunicipalities}}]"
    }

    
}