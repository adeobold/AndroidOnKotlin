package com.android1.androidonkotlin.model.dto


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("fact")
    val fact: Fact?,
    @SerializedName("forecast")
    val forecast: Forecast?,
    @SerializedName("info")
    val info: Info?,
    @SerializedName("now")
    val now: Int,
    @SerializedName("now_dt")
    val nowDt: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Fact::class.java.classLoader),
        parcel.readParcelable(Forecast::class.java.classLoader),
        parcel.readParcelable(Info::class.java.classLoader),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(fact, flags)
        parcel.writeParcelable(forecast, flags)
        parcel.writeParcelable(info, flags)
        parcel.writeInt(now)
        parcel.writeString(nowDt)

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