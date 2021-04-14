package com.jackandphantom.carousellayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jackandphantom.carousellayout.model.DataModel
import com.jackandphantom.carousellayout.adapter.DataAdapter
import com.jackandphantom.carousellayout.databinding.ActivityMainBinding
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

class MainActivity : AppCompatActivity() {
    private val list = ArrayList<DataModel>()
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.carouselRecyclerview.let{
            it.adapter = adapter
            it.set3DItem(true)
            it.setAlpha(true)
        }
    }
}