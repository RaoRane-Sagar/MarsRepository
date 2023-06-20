package com.example.mars_real_estate.overview

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mars_real_estate.databinding.GridViewItemBinding
import com.example.mars_real_estate.network.MarsProperty

class PhotoGridAdapter(val onClickListener: OnClickListener):
    RecyclerView.Adapter<PhotoGridAdapter.MarsPropertyViewHolder>() {

    inner class MarsPropertyViewHolder(val binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MarsProperty,
            newItem: MarsProperty
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        val binding = GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        )
        return MarsPropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        val item = differ.currentList[position]


        holder.binding.apply {

            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    private var onItemClickListener: ((MarsProperty) -> Unit)? = null
    fun setOnItemClickListener(listener: (MarsProperty) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}