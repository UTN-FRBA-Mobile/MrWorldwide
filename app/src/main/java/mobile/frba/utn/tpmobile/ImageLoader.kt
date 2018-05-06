package mobile.frba.utn.tpmobile

import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageLoader{
    companion object {
        fun loadImageIn(imageView: ImageView, url: String){
            Picasso.get().load(url).resize(1920,1080).centerCrop().placeholder(R.drawable.trip_photo_example).into(imageView)
        }
    }

}