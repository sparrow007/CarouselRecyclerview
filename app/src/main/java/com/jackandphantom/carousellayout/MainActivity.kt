package com.jackandphantom.carousellayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jackandphantom.carousellayout.model.DataModel
import com.jackandphantom.carousellayout.adapter.DataAdapter
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

class MainActivity : AppCompatActivity() {
    val list = ArrayList<DataModel>()
    var carouselRecyclerview:CarouselRecyclerview?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        carouselRecyclerview = findViewById<CarouselRecyclerview>(R.id.recycler)
        createItems()
        setRecyclerView()
    }

    private fun createItems(){
        with(list){
            add(DataModel(R.drawable.hacker))
            add(DataModel(R.drawable.hobes))
            add(DataModel(R.drawable.guypro))
            add(DataModel(R.drawable.joker))
            add(DataModel(R.drawable.londonlove))
        }
    }

    private fun setRecyclerView(){
        val adapter = DataAdapter(list)
        carouselRecyclerview?.let{
            it.adapter = adapter
            it.set3DItem(true)
            it.setAlpha(true)
        }
    }
}