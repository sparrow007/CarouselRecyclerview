package com.jackandphantom.carousellayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customviewimple.model.DataModel
import com.jackandphantom.carousellayout.adapter.DataAdapter
import com.jackandphantom.carousellayout.databinding.ActivityMainBinding
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val carouselRecyclerview = findViewById<CarouselRecyclerview>(R.id.recycler)

        val list = ArrayList<DataModel>()
        list.add(DataModel(R.drawable.hacker, "Thi is cool"))
        list.add(DataModel(R.drawable.hobes, "Thi is cool"))
        list.add(DataModel(R.drawable.guypro, "Thi is cool"))
        list.add(DataModel(R.drawable.joker, "Thi is cool"))
        list.add(DataModel(R.drawable.londonlove, "Thi is cool"))

        val adapter = DataAdapter(list)

        carouselRecyclerview.adapter = adapter
        carouselRecyclerview.set3DItem(true)
        carouselRecyclerview.setAlpha(true)
        carouselRecyclerview.setInfinite(true)

        val carouselLayoutManager = carouselRecyclerview.getCarouselLayoutManager()
        carouselLayoutManager.scrollToPosition(4)
    }
}