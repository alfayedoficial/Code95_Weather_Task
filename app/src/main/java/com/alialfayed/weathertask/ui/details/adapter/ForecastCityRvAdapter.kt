package com.alialfayed.weathertask.ui.details.adapter

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import com.alialfayed.weathertask.BR
import com.alialfayed.weathertask.R
import com.alialfayed.weathertask.core.base.adapter.BaseAdapter
import com.alialfayed.weathertask.core.base.adapter.BaseViewHolder
import com.alialfayed.weathertask.core.base.adapter.DiffCallBack
import com.alialfayed.weathertask.core.utils.getBindingRow
import com.alialfayed.weathertask.databinding.ItemRvForcastBinding
import com.alialfayed.weathertask.db.ForecastRow

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 9/13/2021 - 3:28 PM
 */
class ForecastCityRvAdapter: BaseAdapter<ForecastRow>() {

    private var mDiffer = AsyncListDiffer(this, DiffCallBack<ForecastRow>())

    override fun setDataList(dataList: List<ForecastRow>) {
        mDiffer.submitList(dataList)
    }

    override fun addDataList(dataList: List<ForecastRow>) {
        mDiffer.currentList.addAll(dataList)
    }

    override fun clearDataList() {
        mDiffer.currentList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ForecastRow> {
        return ViewHolderForecastCity(getBindingRow(parent, R.layout.item_rv_forcast) as ItemRvForcastBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ForecastRow>, position: Int) {
        val model = mDiffer.currentList[position]
        holder.apply {
            bind(model)
            itemView.apply {
                animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.ver_anim)
            }
        }
    }

    override fun getItemCount(): Int = mDiffer.currentList.size


}

class ViewHolderForecastCity (binding: ItemRvForcastBinding): BaseViewHolder<ForecastRow>(binding)  {

    override var itemRowBinding: ViewDataBinding = binding

    override fun bind(result: ForecastRow) {
        itemRowBinding.setVariable(BR.model , result)
        itemRowBinding.executePendingBindings()
    }
}
