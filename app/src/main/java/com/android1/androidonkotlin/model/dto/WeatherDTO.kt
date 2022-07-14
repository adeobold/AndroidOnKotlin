package com.android1.androidonkotlin.model.dto


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("fact")
    val fact: Fact?,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Fact::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(fact, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherDTO> {
        override fun createFromParcel(parcel: Parcel): WeatherDTO {
            return WeatherDTO(parcel)
        }

        override fun newArray(size: Int): Array<WeatherDTO?> {
            return arrayOfNulls(size)
        }
    }
}