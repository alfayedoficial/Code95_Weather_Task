package com.alialfayed.weathertask.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alialfayed.weathertask.OneSingleActivity
import com.alialfayed.weathertask.R
import com.alialfayed.weathertask.core.base.adapter.CityInterface
import com.alialfayed.weathertask.core.base.view.BaseFragment
import com.alialfayed.weathertask.core.utils.AppConstants
import com.alialfayed.weathertask.core.utils.AppConstants.CITY
import com.alialfayed.weathertask.core.utils.AppConstants.ID
import com.alialfayed.weathertask.core.utils.ExtensionsApp.isFirst
import com.alialfayed.weathertask.core.utils.setLogCat
import com.alialfayed.weathertask.core.utils.setSnackBar
import com.alialfayed.weathertask.data.ResultData
import com.alialfayed.weathertask.databinding.FragmentHomeBinding
import com.alialfayed.weathertask.domain.model.WeatherCityResponse
import com.alialfayed.weathertask.db.CityModel
import com.alialfayed.weathertask.ui.home.adapter.CityRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_one_single.*
import kotlinx.android.synthetic.main.base_recyclerview.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@AndroidEntryPoint
class HomeFragment(override val layoutResourceLayout: Int = R.layout.fragment_home) :
    BaseFragment<FragmentHomeBinding>() , CityInterface {

    private val adapterCity : CityRvAdapter by lazy { CityRvAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return rootView
    }

    override fun onFragmentCreated(dataBinder: FragmentHomeBinding) {
        dataBinder.apply {
            fragment = this@HomeFragment
            lifecycleOwner = this@HomeFragment
        }
    }

    override fun setUpViewModelStateObservers() {
        (requireActivity() as OneSingleActivity).apply {

            tvSearch.setOnItemClickListener { adapterView, view, position, id ->
                mViewModel.run {
                    placeIdArrayList[position].let { placeId ->
                        lifecycleScope.launch {
                            val result = withContext(Dispatchers.Default) {
                                getGooglePlaceOfLatLon(placeId)
                            }
                            when (result) {
                                is ResultData.Failure -> result.msg?.let { msg -> setSnackBar(msg).show()}
                                is ResultData.Loading -> { showLoadingView()}
                                is ResultData.Internet -> { showNoInternetView()}
                                is ResultData.Success -> {
                                    val location = result.data?.result?.geometry?.location
                                    location?.let {
                                        getWeatherOfLatLon(it).observe(requireActivity(),{ result ->
                                            if (result is ResultData.Success){
                                                val bundle = bundleOf(CITY to result.data?.name)
                                                navController.navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
                                                tvSearch.setText("")
                                            }
                                            fetchResult(result) })
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }.mViewModel.apply {
            readyToFetch.observe(requireActivity(), { requirements ->
                requirements?.let {
                    if (it["network"] == true) {
                        if (!isFirst(appPreferences)){
                            if (!isFirst(appPreferences) && it["gps"] == true) {
                                fetchWeatherLatLong().observe(requireActivity(), { result -> fetchResult(result) })
                            } else {
                                fetchWeatherByCity("London").observe(requireActivity(), { result -> fetchResult(result) })
                            }
                        }
                    }
                }
            })
        }

        getCities()

    }

    private fun fetchResult(result: ResultData<WeatherCityResponse>) {
        when (result) {
            is ResultData.Success -> { result.data?.let { data -> insertModel(data)  } }
            is ResultData.Failure -> result.msg?.let { msg -> setSnackBar(msg).show() }
            is ResultData.Loading -> { showLoadingView()}
            is ResultData.Internet -> { showNoInternetView()}
        }
    }

    private fun insertModel(model: WeatherCityResponse) {
        if (!isFirst(appPreferences))  appPreferences.setValue(AppConstants.IS_FIRST,true)
        lifecycleScope.launch {

            val resultSize = withContext(Dispatchers.Default){
                (requireActivity() as OneSingleActivity).mViewModel.getSizeCities()
            }

            when (resultSize) {
                is ResultData.Failure -> resultSize.msg?.let { msg -> setSnackBar(msg).show()}
                is ResultData.Loading -> { }
                is ResultData.Internet -> { }
                is ResultData.Success -> {

                    resultSize.data?.let{ size ->
                        if (size < 5){
                            val result = withContext(Dispatchers.Default) {
                                (requireActivity() as OneSingleActivity).mViewModel.insertCity(CityModel(name = model.name , temp = model.main?.temp
                                    , icon = model.weather?.first()?.icon))
                            }
                            when (result) {
                                is ResultData.Failure -> result.msg?.let { msg -> setSnackBar(msg).show()}
                                is ResultData.Loading -> { }
                                is ResultData.Internet -> { }
                                is ResultData.Success -> { getCities() }
                            }
                        }
                    }

                }
            }


        }
    }

    private fun getCities() {
        lifecycleScope.launch {
            val result = withContext(Dispatchers.Default){
                (requireActivity() as OneSingleActivity).mViewModel.getCities()
            }

            when (result) {
                is ResultData.Failure -> result.msg?.let { msg -> setSnackBar(msg).show()}
                is ResultData.Loading -> { }
                is ResultData.Internet -> { }
                is ResultData.Success -> { result.data?.let {models -> showMainView(models) }}
            }
        }
    }

    /**
     * layout of No Internet
     */
    private fun showNoInternetView(){
        dataBinder.apply {
            viewFlipper.displayedChild = viewFlipper.indexOfChild(viewNoInternet.root)
            viewNoInternet.cardTryAgain.setOnClickListener {
                extensions.openWifi()
            }
        }
    }

    /**
     * layout of No Loading
     */
    private fun showLoadingView(){
        dataBinder.apply {
            viewFlipper.displayedChild = viewFlipper.indexOfChild(viewLoading.root)
        }
    }

    /**
     * layout of Main
     */
    private fun showMainView(models: List<CityModel>) {

        dataBinder.apply {
            viewFlipper.displayedChild = viewFlipper.indexOfChild(rvCities.rootView)

            adapterCity.setDataList(models)
            adapterCity.interfaceInit(this@HomeFragment)

            rvCities.baseRecyclerview.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, 2 , RecyclerView.VERTICAL, false)
                adapter =adapterCity
            }
        }


    }

    override fun onItemClick(city: String , id: Long) {
        val bundle = bundleOf(CITY to city , ID to id )
        navController.navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }

    override fun onItemDeleted(id: Long) {
        (requireActivity() as OneSingleActivity).mViewModel.apply {
            lifecycleScope.launch {
                val result = withContext(Dispatchers.Default) {
                    deleteCity(id)
                }
                when (result) {
                    is ResultData.Failure -> result.msg?.let { msg -> setSnackBar(msg).show() }
                    is ResultData.Loading -> { }
                    is ResultData.Internet -> { }
                    is ResultData.Success -> {
                        this@HomeFragment.getCities()
                        val resultDeleteForecastCity = withContext(Dispatchers.Default) {
                           deleteForecastCity(id)
                        }

                        if (resultDeleteForecastCity is  ResultData.Success) run {
                            setLogCat("TESET_DELETE_RESULT" , "resultDeleteForecastCity")
                        }
                    }
                }
            }
        }

    }


}