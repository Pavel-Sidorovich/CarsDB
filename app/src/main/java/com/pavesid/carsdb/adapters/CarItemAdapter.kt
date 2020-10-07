package com.pavesid.carsdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pavesid.carsdb.R
import com.pavesid.carsdb.data.local.CarItem
import javax.inject.Inject

class CarItemAdapter @Inject constructor() :
    RecyclerView.Adapter<CarItemAdapter.CarItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<CarItem>() {

        override fun areItemsTheSame(oldItem: CarItem, newItem: CarItem) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CarItem, newItem: CarItem) =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var carItems: List<CarItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarItemViewHolder =
        CarItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_car,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CarItemViewHolder, position: Int) {
        holder.bind(carItems[position])
    }

    override fun getItemCount(): Int = carItems.size

    inner class CarItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val carPrice = itemView.findViewById<TextView>(R.id.price)
        private val carTitle = itemView.findViewById<TextView>(R.id.car_title)
        private val carClass = itemView.findViewById<TextView>(R.id.car_class)
        private val carEngineType = itemView.findViewById<TextView>(R.id.car_engine_type)

        fun bind(carItem: CarItem) {
            carPrice.text = carItem.carPrice
            carClass.text = carItem.carClass
            carEngineType.text = carItem.engineType
            carTitle.text =
                itemView.context.getString(R.string.title, carItem.carBrand, carItem.carModel)
        }
    }
}