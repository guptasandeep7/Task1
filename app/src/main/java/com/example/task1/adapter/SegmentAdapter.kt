package com.example.task1.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R
import com.example.task1.databinding.ItemSeekbarBinding
import com.example.task1.model.Segment

class SegmentAdapter : RecyclerView.Adapter<SegmentAdapter.ViewHolder>() {

    private var segmentList = mutableListOf(Segment(1, 100))
    private var mlistener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(seekBar: SeekBar, holder: ViewHolder)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mlistener = listener
    }

    class ViewHolder(val binding: ItemSeekbarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(segment: Segment) {
            binding.segment = segment
            binding.seekbar.progress = segment.endValue
            binding.seekbar.min = segment.startValue
            binding.seekbar.max = segment.endValue
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSeekbarBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_seekbar, parent, false
        )
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(segmentList[position])

        holder.binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, value: Int, p2: Boolean) {
                holder.binding.endTv.text = value.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let { mlistener?.onItemClick(it, holder) }
            }
        })
    }

    override fun getItemCount(): Int {
        return segmentList.size
    }

    fun addSegment(segment: Segment, position: Int) {
        segmentList.add(position, segment)
        notifyItemInserted(position)
    }

    fun clearAll() {
        segmentList.clear()
        segmentList.add(Segment(1,100))
        notifyDataSetChanged()
    }

    fun removeSegment(position: Int) {
        segmentList[position - 1].endValue = segmentList[position].endValue
        notifyItemChanged(position - 1)
        segmentList.removeAt(position)
        notifyItemRemoved(position)
    }


}