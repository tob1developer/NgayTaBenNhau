package com.kietngo.ngaytabennhau.ui.dialog.color

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.ui.model.ColorUi

class ColorAdapter : ListAdapter<ColorUi, ColorAdapter.ColorViewHolder>(ColorDiffUtil) {

    //var onNavigateUp: (color : String) -> Unit = {}
    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageView  = itemView.findViewById<ImageView>(R.id.imageColor)

        fun bind(colorUi: ColorUi){

            imageView.setColorFilter(Color.parseColor(colorUi.color.ColorToHex))

            itemView.setOnClickListener {
                colorUi.onClick()
              //  onNavigateUp(colorUi.color.ColorToHex)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object ColorDiffUtil : DiffUtil.ItemCallback<ColorUi>(){
        override fun areItemsTheSame(oldItem: ColorUi, newItem: ColorUi): Boolean {
            return oldItem.color == newItem.color
        }

        override fun areContentsTheSame(oldItem: ColorUi, newItem: ColorUi): Boolean {
            return oldItem == newItem
        }

    }

}