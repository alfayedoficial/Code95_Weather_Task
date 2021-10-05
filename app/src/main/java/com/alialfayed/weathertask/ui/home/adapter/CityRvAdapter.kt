package com.alialfayed.weathertask.ui.home.adapter

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import com.alialfayed.weathertask.BR
import com.alialfayed.weathertask.R
import com.alialfayed.weathertask.core.base.adapter.BaseAdapter
import com.alialfayed.weathertask.core.base.adapter.BaseViewHolder
import com.alialfayed.weathertask.core.base.adapter.CityInterface
import com.alialfayed.weathertask.core.base.adapter.DiffCallBack
import com.alialfayed.weathertask.core.utils.getBindingRow
import com.alialfayed.weathertask.databinding.ItemRvCityWeatherBinding
import com.alialfayed.weathertask.db.CityModel

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 9/13/2021 - 3:28 PM
 */
class CityRvAdapter: BaseAdapter<CityModel>() {

    private var mDiffer = AsyncListDiffer(this, DiffCallBack<CityModel>())
    private lateinit var interfaceCity: CityInterface

    override fun setDataList(dataList: List<CityModel>) {
        mDiffer.submitList(dataList)
    }

    override fun addDataList(dataList: List<CityModel>) {
        mDiffer.currentList.addAll(dataList)
    }

    override fun clearDataList() {
        mDiffer.currentList.clear()
    }

    fun interfaceInit(interfaceCity: CityInterface) {
        this.interfaceCity = interfaceCity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CityModel> {
        return ViewHolderCity(getBindingRow(parent, R.layout.item_rv_city_weather) as ItemRvCityWeatherBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<CityModel>, position: Int) {
        val model = mDiffer.currentList[position]
        holder.apply {
            bind(model)
            itemView.apply {
                animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.ver_anim)
            }

            itemView.setOnClickListener {
                model.name?.let { city -> interfaceCity.onItemClick(city , model.id) }
            }

            (itemRowBinding as ItemRvCityWeatherBinding).btnClose.setOnClickListener {
                model.id.let { id -> interfaceCity.onItemDeleted(id) }
            }
        }
    }

    override fun getItemCount(): Int = mDiffer.currentList.size


}

class ViewHolderCity (binding: ItemRvCityWeatherBinding): BaseViewHolder<CityModel>(binding)  {

    override var itemRowBinding: ViewDataBinding = binding

    override fun bind(result: CityModel) {
        itemRowBinding.setVariable(BR.model , result)
        itemRowBinding.executePendingBindings()
    }
}
