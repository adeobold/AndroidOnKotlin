package com.android1.androidonkotlin.model.dto


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("condition")
    val condition: String?,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("pressure_mm")
    val pressureMm: Double,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("humidity")
    val humidity: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(condition)
        parcel.writeDouble(feelsLike)
        parcel.writeDouble(pressureMm)
        parcel.writeDouble(temp)
        parcel.writeDouble(windSpeed)
        parcel.writeDouble(humidity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Fact> {
        override fun createFromParcel(parcel: Parcel): Fact {
            return Fact(parcel)
        }

        override fun newArray(size: Int): Array<Fact?> {
            return arrayOfNulls(size)
        }
    }
}