package com.alialfayed.weathertask.ui.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alialfayed.weathertask.OneSingleActivity
import com.alialfayed.weathertask.R
import com.alialfayed.weathertask.core.base.view.BaseFragment
import com.alialfayed.weathertask.core.utils.*
import com.alialfayed.weathertask.core.utils.AppConstants.CITY
import com.alialfayed.weathertask.core.utils.AppConstants.ID
import com.alialfayed.weathertask.data.ResultData
import com.alialfayed.weathertask.databinding.FragmentCityDetailsBinding
import com.alialfayed.weathertask.db.ForecastCityModel
import com.alialfayed.weathertask.db.ForecastRow
import com.alialfayed.weathertask.domain.model.forecest.ForecastCityResponse
import com.alialfayed.weathertask.ui.details.adapter.ForecastCityRvAdapter
import com.alialfayed.weathertask.ui.details.viewModel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.base_recyclerview.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

@AndroidEntryPoint
class CityDetailsFragment (override val layoutResourceLayout: Int = R.layout.fragment_city_details) : BaseFragment<FragmentCityDetailsBinding>() {

    private var city: String? = null
    private var id: Long? = null
    private val viewModel : DetailsViewModel by viewModels()
    private val adapterForecastCity : ForecastCityRvAdapter by lazy { ForecastCityRvAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city = it.getString(CITY)
            id =  it.getLong(ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return rootView
    }

    override fun onFragmentCreated(dataBinder: FragmentCityDetailsBinding) {
        dataBinder.apply {
            fragment = this@CityDetailsFragment
            lifecycleOwner = this@CityDetailsFragment
        }
    }

    override fun setUpViewModelStateObservers() {
        (requireActivity() as OneSingleActivity).mViewModel.apply {
            readyToFetch.observe(requireActivity(), { requirements ->
                requirements?.let {
                    if (it["network"] == true) {
                        viewModel.findCityForecastData(city!!).observe(requireActivity(), { result -> fetchResult(result) })
                    }else{
                        if (id != null) fetchResultLocal()
                    }
                }
            })
        }
    }

    private fun fetchResultLocal() {
        lifecycleScope.launch {
            val result = withContext(Dispatchers.Main){
                viewModel.getForecastCity(id!!)
            }

            when (result) {
                is ResultData.Failure -> result.msg?.let { msg -> setSnackBar(msg).show()}
                is ResultData.Loading -> { }
                is ResultData.Internet -> { }
                is ResultData.Success -> { result.data?.let {model -> showMainView(model) }}
            }
        }
    }

    private fun fetchResult(result: ResultData<ForecastCityResponse>) {
        when (result) {
            is ResultData.Success -> { result.data?.let { data ->
                insertData(data)
                if (id != null)updateData(data)
            } }
            is ResultData.Failure -> result.msg?.let { msg -> setSnackBar(msg).show() }
            is ResultData.Loading -> { showLoadingView()}
            is ResultData.Internet -> { showNoInternetView()}
        }
    }

    private fun insertData(data: ForecastCityResponse) {
        val forecastRows = ArrayList<ForecastRow>()
        data.list?.forEach { item ->
            forecastRows.add(ForecastRow(main = item.weather?.first()?.main,
                icon = item.weather?.first()?.icon, time = item.dtTxt ,
                temp = item.main?.temp))
        }
        val forecastCityModel = ForecastCityModel(
            id = id , name = data.city?.name ,
            temp = data.list?.first()?.main?.temp,
            icon =  data.list?.first()?.weather?.first()?.icon,
            time = data.list?.first()?.dtTxt, forecastRows = forecastRows
        )

        if (id != null){
            lifecycleScope.launch {
                val result = withContext(Dispatchers.Default){
                    viewModel.insertForecastCity(forecastCityModel)
                }

                when (result) {
                    is ResultData.Failure -> result.msg?.let { msg -> setSnackBar(msg).show()}
                    is ResultData.Loading -> { }
                    is ResultData.Internet -> { }
                    is ResultData.Success -> { result.data?.let {showMainView(forecastCityModel) }}
                }
            }
        }else{
            showMainView(forecastCityModel)
        }

    }

    private fun updateData(data: ForecastCityResponse) {
        print("")
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
    private fun showMainView(data: ForecastCityModel) {

        dataBinder.apply {
            viewFlipper.displayedChild = viewFlipper.indexOfChild(containerDetails.root)

            containerDetails.apply {
                setBindImage(imgWeather , data.icon)
                setBindString(tvCity , data.name)
                setBindString(tvDate , data.time)
                setBindTemp(tvTemp , data.temp)

                data.forecastRows?.let {models -> adapterForecastCity.setDataList(models)  }

                rvForecast.baseRecyclerview.apply {

                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    adapter =adapterForecastCity
                }

            }
        }


    }

}