package com.zhang.apphide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zhang.apphide.R
import com.zhang.apphide.databinding.ItemAppBinding
import com.zhang.apphide.model.AppInfo
import com.zhang.apphide.viewmodel.AppViewModel

class AppAdapter(var data: MutableList<AppInfo>) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAppBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_app,
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appInfo = data[position]
        holder.bindAppInfo(appInfo)
    }

    override fun getItemCount(): Int = data.size

    fun setNewData(newData: MutableList<AppInfo>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemAppBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindAppInfo(appInfo: AppInfo) {
            binding.starBtn.setOnFavoriteChangeListener{
                button, favorite ->
                if (button.isPressed) {
                    binding.viewModel?.onFavoriteChange(favorite)
                }
            }
            binding.switchBtn.setOnCheckedChangeListener {
                button, check ->
                if (button.isPressed) {
                    binding.viewModel?.onCheckChange(check)
                }
            }
            binding.switchBtn.isChecked = appInfo.hidden
            binding.viewModel = AppViewModel(itemView.context, appInfo)
        }
    }

}