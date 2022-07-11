package com.android1.androidonkotlin.model.dto


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Part(
    @SerializedName("condition")
    val condition: String?,
    @SerializedName("daytime")
    val daytime: String?,
    @SerializedName("feels_like")
    val feelsLike: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("part_name")
    val partName: String?,
    @SerializedName("polar")
    val polar: Boolean,
    @SerializedName("prec_mm")
    val precMm: Double,
    @SerializedName("prec_period")
    val precPeriod: Int,
    @SerializedName("prec_prob")
    val precProb: Int,
    @SerializedName("pressure_mm")
    val pressureMm: Int,
    @SerializedName("pressure_pa")
    val pressurePa: Int,
    @SerializedName("temp_avg")
    val tempAvg: Int,
    @SerializedName("temp_max")
    val tempMax: Int,
    @SerializedName("temp_min")
    val tempMin: Int,
    @SerializedName("wind_dir")
    val windDir: String?,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(condition)
        parcel.writeString(daytime)
        parcel.writeInt(feelsLike)
        parcel.writeInt(humidity)
        parcel.writeString(icon)
        parcel.writeString(partName)
        parcel.writeByte(if (polar) 1 else 0)
        parcel.writeDouble(precMm)
        parcel.writeInt(precPeriod)
        parcel.writeInt(precProb)
        parcel.writeInt(pressureMm)
        parcel.writeInt(pressurePa)
        parcel.writeInt(tempAvg)
        parcel.writeInt(tempMax)
        parcel.writeInt(tempMin)
        parcel.writeString(windDir)
        parcel.writeDouble(windGust)
        parcel.writeDouble(windSpeed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Part> {
        override fun createFromParcel(parcel: Parcel): Part {
            return Part(parcel)
        }

        override fun newArray(size: Int): Array<Part?> {
            return arrayOfNulls(size)
        }
    }
}