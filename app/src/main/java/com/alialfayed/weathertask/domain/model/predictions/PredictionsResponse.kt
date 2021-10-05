package com.alialfayed.weathertask.domain.model.predictions

import com.google.gson.annotations.SerializedName

data class PredictionsResponse(

	@field:SerializedName("predictions")
	val predictions: List<PredictionsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class PredictionsItem(

	@field:SerializedName("reference")
	val reference: String? = null,

	@field:SerializedName("structured_formatting")
	val structuredFormatting: StructuredFormatting? = null,

	@field:SerializedName("place_id")
	val placeId: String? = null ,

	@field:SerializedName("description")
	val description: String? = null

)

data class StructuredFormatting(

	@field:SerializedName("main_text")
	val mainText: String? = null
)
