package com.example.mars_real_estate

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mars_real_estate.network.MarsProperty
import com.example.mars_real_estate.overview.MarsApiStatus
import com.example.mars_real_estate.overview.PhotoGridAdapter


@BindingAdapter("status")
fun bindStatus(statusImage: ImageView, status: MarsApiStatus? ){
    when(status){

        MarsApiStatus.LOADING -> {
            statusImage.visibility = View.VISIBLE
            statusImage.setImageResource(R.drawable.loading_animation)
        }

        MarsApiStatus.ERROR -> {
            statusImage.visibility = View.VISIBLE
            statusImage.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImage.visibility = View.GONE
        }

        else -> {}
    }

}



@BindingAdapter("img_src")
fun bindImage(imageView: ImageView, imgUrl: String?){

    imgUrl?.let {
        val imageUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imageUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imageView)
    }

}


