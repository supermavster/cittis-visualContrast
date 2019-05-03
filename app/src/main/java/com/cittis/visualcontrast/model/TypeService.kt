package com.cittis.visualcontrast.model

import android.os.Parcel
import android.os.Parcelable

data class TypeService(
    var idTypeService: Int,
    var nameTypeService: String
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TypeService> = object : Parcelable.Creator<TypeService> {
            override fun newArray(size: Int): Array<TypeService?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel): TypeService = TypeService(source)
        }
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            idTypeService?.let { it1 -> dest.writeInt(it1) }
            dest.readString()
        }
    }

    override fun describeContents(): Int = 0


}