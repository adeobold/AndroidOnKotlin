package com.android1.androidonkotlin.domain

import android.os.Parcel
import android.os.Parcelable

data class WeatherItem(
    val city: City? = getDefaultCity(), var temperature: Double = 21.0,
    var pressure: Double = 747.0, var humidity: Double = 20.0, var wind: Double = 5.0): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(City::class.java.classLoader),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(city, flags)
        parcel.writeDouble(temperature)
        parcel.writeDouble(pressure)
        parcel.writeDouble(humidity)
        parcel.writeDouble(wind)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherItem> {
        override fun createFromParcel(parcel: Parcel): WeatherItem {
            return WeatherItem(parcel)
        }

        override fun newArray(size: Int): Array<WeatherItem?> {
            return arrayOfNulls(size)
        }
    }
}

data class City(val name: String?, val lat: Double, val lon: Double): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeDouble(lat)
        parcel.writeDouble(lon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }
}

fun getDefaultCity() = City("Москва", 55.755826, 37.617299900000035)

fun getWorldCities(): List<WeatherItem> {
    return listOf(
        WeatherItem(City("Лондон", 51.5085300, -0.1257400), 1.0, 2.0),
        WeatherItem(City("Токио", 35.6895000, 139.6917100),3.0, 4.0),
        WeatherItem(City("Париж", 48.8534100, 2.3488000), 5.0, 6.0),
        WeatherItem(City("Берлин", 52.52000659999999, 13.404953999999975),7.0, 8.0),
        WeatherItem(City("Рим", 41.9027835, 12.496365500000024), 9.0, 10.0),
        WeatherItem(City("Минск", 53.90453979999999, 27.561524400000053), 11.0, 12.0),
        WeatherItem(City("Стамбул", 41.0082376, 28.97835889999999), 13.0, 14.0),
        WeatherItem(City("Вашингтон", 38.9071923, -77.03687070000001), 15.0, 16.0),
        WeatherItem(City("Киев", 50.4501, 30.523400000000038),17.0, 18.0),
        WeatherItem(City("Пекин", 39.90419989999999, 116.40739630000007),19.0, 20.0)
    )
}

fun getRussianCities(): List<WeatherItem> {
    return listOf(
        WeatherItem(City("Москва", 55.755826, 37.617299900000035), 1.0, 2.0),
        WeatherItem(City("Санкт-Петербург", 59.9342802, 30.335098600000038),3.0, 3.0),
        WeatherItem(City("Новосибирск", 55.00835259999999, 82.93573270000002), 5.0, 6.0),
        WeatherItem(City("Екатеринбург", 56.83892609999999, 60.60570250000001),7.0, 8.0),
        WeatherItem(City("Нижний Новгород", 56.2965039, 43.936059), 9.0, 10.0),
        WeatherItem(City("Казань", 55.8304307, 49.06608060000008), 11.0, 12.0),
        WeatherItem(City("Челябинск", 55.1644419, 61.4368432), 13.0, 14.0),
        WeatherItem(City("Омск", 54.9884804, 73.32423610000001),15.0, 16.0),
        WeatherItem(City("Ростов-на-Дону", 47.2357137, 39.701505),17.0, 18.0),
        WeatherItem(City("Уфа", 54.7387621, 55.972055400000045), 19.0, 20.0)
    )
}


