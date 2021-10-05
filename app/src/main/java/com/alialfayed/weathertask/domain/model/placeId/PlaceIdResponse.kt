package com.alialfayed.weathertask.domain.model.placeId

import com.google.gson.annotations.SerializedName

data class PlaceIdResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Result(

	@field:SerializedName("geometry")
	val geometry: Geometry? = null,

	@field:SerializedName("place_id")
	val placeId: String? = null
)

data class Geometry(

	@field:SerializedName("location")
	val location: Location? = null
)

data class Location(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)


