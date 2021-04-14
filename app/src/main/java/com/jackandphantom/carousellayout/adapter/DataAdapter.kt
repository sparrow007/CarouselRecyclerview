package com.jackandphantom.carousellayout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.customviewimple.model.DataModel
import com.jackandphantom.carousellayout.R
import com.jackandphantom.carouselrecyclerview.view.ReflectionImageView

class DataAdapter (private var list : List<DataModel>): RecyclerView.Adapter<DataAdapter.ViewHolder>() {

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val image : ImageView = itemView.findViewById(R.id.image)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent,false)
         return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.image).load(list.get(position).img).into(holder.image)
    }

    fun updateData(list: List<DataModel>) {
        this.list = list
        notifyDataSetChanged()
    }
}