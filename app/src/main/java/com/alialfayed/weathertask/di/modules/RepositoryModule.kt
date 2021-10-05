package com.alialfayed.weathertask.di.modules

import android.app.Application
import com.alialfayed.weathertask.domain.api.ApiGooglePlaces
import com.alialfayed.weathertask.ui.home.repo.HomeRepository
import com.alialfayed.weathertask.domain.api.ApiService
import com.alialfayed.weathertask.db.CityDao
import com.alialfayed.weathertask.db.ForecastCityDao
import com.alialfayed.weathertask.ui.details.repo.DetailsRepository
import com.alialfayed.weathertask.ui.details.repo.DetailsRepositoryImp
import com.alialfayed.weathertask.ui.home.data.LocationProvider
import com.alialfayed.weathertask.ui.home.repo.HomeRepositoryImp
import com.alialfayed.weathertask.widget.WidgetRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {


    @Provides
    fun provideLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application.applicationContext)
    }

    @Provides
    fun provideLocationProvider(context: Application, client: FusedLocationProviderClient
    ): LocationProvider {
        return LocationProvider(context.applicationContext, client)
    }

    @Provides
    fun providesHomeRepository(homeRepositoryImp: HomeRepositoryImp, locationProvider: LocationProvider): HomeRepository {
        return HomeRepository(homeRepositoryImp , locationProvider)
    }

    @Provides
    fun providesHomeRepositoryImp(apiService: ApiService, apiGooglePlaces : ApiGooglePlaces  , cityDao : CityDao): HomeRepositoryImp {
        return HomeRepositoryImp(apiService  , apiGooglePlaces  , cityDao)
    }

    @Provides
    fun providesDetailsRepository(detailsRepositoryImp: DetailsRepositoryImp): DetailsRepository {
        return DetailsRepository(detailsRepositoryImp )
    }

    @Provides
    fun providesDetailsRepositoryImp(apiService: ApiService , forecastCityDao : ForecastCityDao): DetailsRepositoryImp {
        return DetailsRepositoryImp(apiService , forecastCityDao)
    }

    @Provides
    fun providesWidgetRepository(cityDao : CityDao): WidgetRepository {
        return WidgetRepository(cityDao)
    }





}