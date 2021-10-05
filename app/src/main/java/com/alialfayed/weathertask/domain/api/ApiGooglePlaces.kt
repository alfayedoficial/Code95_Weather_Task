package com.alialfayed.weathertask.domain.api

import com.alialfayed.weathertask.core.utils.AppConstants
import com.alialfayed.weathertask.core.utils.AppConstants.GOOGLE_PLACE_API_QUERY
import com.alialfayed.weathertask.domain.model.placeId.PlaceIdResponse
import com.alialfayed.weathertask.domain.model.predictions.PredictionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGooglePlaces {

  @GET("autocomplete/json")
  suspend fun getPredictions(
    @Query("input") searchText: String,
    @Query(GOOGLE_PLACE_API_QUERY) key: String = AppConstants.GOOGLE_PLACE_API_KEY
  ): Response<PredictionsResponse>

  @GET("details/json")
  suspend fun fetchGooglePlaceOfLatLon(
    @Query("placeid") placeId: String,
    @Query(GOOGLE_PLACE_API_QUERY) key: String = AppConstants.GOOGLE_PLACE_API_KEY
  ): Response<PlaceIdResponse>

}