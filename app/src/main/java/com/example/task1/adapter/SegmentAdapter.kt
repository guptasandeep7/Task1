package com.example.task1.adapter

import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.res.ResourcesCompat.getDrawable
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

            override fun onProgressChanged(seekBar: SeekBar?, value: Int, p2: Boolean) {
                holder.binding.endTv.text = value.toString()
                if (value == seekBar?.min) {
                    holder.binding.endTv.foreground =
                        holder.itemView.resources.getDrawable(R.drawable.ic_delete_24)
                } else {
                    holder.binding.endTv.foreground = null
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                holder.binding.startTv.foreground =
                    holder.itemView.resources.getDrawable(R.drawable.ic_delete_24)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                holder.binding.startTv.foreground = null
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
        segmentList.add(Segment(1, 100))
        notifyDataSetChanged()
    }

    fun removeSegment(position: Int, max: Int) {
        segmentList[position - 1].endValue = max
        notifyItemChanged(position - 1)
        segmentList.removeAt(position)
        notifyItemRemoved(position)
    }


}