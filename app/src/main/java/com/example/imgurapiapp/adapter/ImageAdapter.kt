package com.example.imgurapiapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imgurapiapp.R
import com.example.imgurapiapp.StringsConstant
import com.example.imgurapiapp.common.gone
import com.example.imgurapiapp.common.visible
import com.example.imgurapiapp.databinding.ImgurRecyclerviewItemsBinding
import com.example.imgurapiapp.model.ImgurData
import com.example.imgurapiapp.utils.HelperUtility
import javax.inject.Inject

class ImageAdapter @Inject constructor() : ListAdapter<ImgurData, ImageAdapter.ItemVH>(
    SuggestionImgurDiffCallBacks()
) {

    class SuggestionImgurDiffCallBacks : DiffUtil.ItemCallback<ImgurData>() {
        override fun areItemsTheSame(oldItem: ImgurData, newItem: ImgurData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImgurData, newItem: ImgurData): Boolean {
            return oldItem == newItem
        }
    }

    class ItemVH(val binding: ImgurRecyclerviewItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val inflater = LayoutInflater.from(parent.context)
        return ItemVH(ImgurRecyclerviewItemsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        bindItem(holder, getItem(position))
    }

    private fun bindItem(holder: ItemVH, item: ImgurData) {

        holder.binding.apply {
            var images = ""
            if (item.cover != null) {
                images = StringsConstant.IMAGE_BASE_URL + item.cover + ".png"
            } else if (item.link != null) {
                images = item.link

            }
            Glide.with(this.root).load(images)
                .error(R.drawable.no_image).placeholder(R.drawable.no_image).into(itemImage)

            itemTitle.text = item.title
            val date = HelperUtility.convertTime(item.datetime)
            itemDate.text = date
            val imageCount = item.imagesCount
            imageCountLayout.gone()
            if (imageCount>1){
                imageCountLayout.visible()
                itemNoImages.text = imageCount.toString()
            }

        }

    }


}