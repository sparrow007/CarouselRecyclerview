package com.jackandphantom.carousellayout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customviewimple.model.DataModel
import com.jackandphantom.carousellayout.adapter.DataAdapter
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

class MainActivity : AppCompatActivity(), DataAdapter.OnItemClickListener {
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

        val adapter = DataAdapter(list, this)

        carouselRecyclerview.adapter = adapter
        carouselRecyclerview.setAlpha(true)

        val carouselLayoutManager = carouselRecyclerview.getCarouselLayoutManager()
        val currentlyCenterPosition = carouselRecyclerview.getSelectedPosition()

        carouselLayoutManager.scrollToPosition(4)
    }

    override fun onnItemClick(position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }
}