package com.alialfayed.weathertask.domain.model.forecest

import com.google.gson.annotations.SerializedName

data class ForecastCityResponse(

	@field:SerializedName("city")
	val city: City? = null,

	@field:SerializedName("cnt")
	val cnt: Int? = null,

	@field:SerializedName("cod")
	val cod: String? = null,

	@field:SerializedName("message")
	val message: Int? = null,

	@field:SerializedName("list")
	val list: List<ListItem>? = null
)

data class ListItem(

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("pop")
	val pop: Double? = null,

	@field:SerializedName("visibility")
	val visibility: Int? = null,

	@field:SerializedName("dt_txt")
	val dtTxt: String? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("main")
	val main: Main? = null,

	@field:SerializedName("clouds")
	val clouds: Clouds? = null,

	@field:SerializedName("sys")
	val sys: Sys? = null,

	@field:SerializedName("wind")
	val wind: Wind? = null,

	@field:SerializedName("rain")
	val rain: Rain? = null
)

data class WeatherItem(

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("main")
	val main: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class City(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("coord")
	val coord: Coord? = null,

	@field:SerializedName("sunrise")
	val sunrise: Int? = null,

	@field:SerializedName("timezone")
	val timezone: Int? = null,

	@field:SerializedName("sunset")
	val sunset: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("population")
	val population: Int? = null
)

data class Wind(

	@field:SerializedName("deg")
	val deg: Int? = null,

	@field:SerializedName("speed")
	val speed: Double? = null,

	@field:SerializedName("gust")
	val gust: Double? = null
)

data class Rain(

	@field:SerializedName("3h")
	val jsonMember3h: Double? = null
)

data class Clouds(

	@field:SerializedName("all")
	val all: Int? = null
)

data class Main(

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("temp_min")
	val tempMin: Double? = null,

	@field:SerializedName("grnd_level")
	val grndLevel: Int? = null,

	@field:SerializedName("temp_kf")
	val tempKf: Double? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("pressure")
	val pressure: Int? = null,

	@field:SerializedName("sea_level")
	val seaLevel: Int? = null,

	@field:SerializedName("feels_like")
	val feelsLike: Double? = null,

	@field:SerializedName("temp_max")
	val tempMax: Double? = null
)

data class Sys(

	@field:SerializedName("pod")
	val pod: String? = null
)

data class Coord(

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)
