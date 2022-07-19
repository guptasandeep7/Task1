package com.example.task1.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R
import com.example.task1.adapter.SegmentAdapter
import com.example.task1.model.Segment

class MainActivity : AppCompatActivity() {

    private val segmentAdapter = SegmentAdapter()
    private val segmentList = mutableListOf<Segment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.seekbar_rv)

        segmentList.add(Segment(1, 32, Color.parseColor("#EF5350")))
        segmentList.add(Segment(33, 56, Color.parseColor("#FFA726")))
        segmentList.add(Segment(57, 85, Color.parseColor("#29B6F6")))
        segmentList.add(Segment(86, 100, Color.parseColor("#66BB6A")))

        segmentAdapter.initSegment(segmentList)
        recyclerView.adapter = segmentAdapter

        segmentAdapter.setOnItemClickListener(object : SegmentAdapter.OnItemClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClick(seekBar: SeekBar, holder: SegmentAdapter.ViewHolder) {
                seekBar.progress.let {
                    when {
                        it == seekBar.min -> {
                            if (holder.adapterPosition == 0) {
                                segmentAdapter.clearAll()
                            } else {
                                segmentAdapter.removeSegment(holder.adapterPosition,seekBar.max)
                            }
                        }

                        (seekBar.max - it) > 1 && (it - seekBar.min) > 0 -> {
                            segmentAdapter.addSegment(Segment(it + 1, seekBar.max,Color.RED),holder.adapterPosition + 1)
                            holder.binding.endTv.text = (it).toString()
                            seekBar.max = it
                        }
                        else -> {
                            seekBar.progress = seekBar.max
                            Toast.makeText(
                                this@MainActivity,
                                "Minimum segment length is 2!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        })

    }
}