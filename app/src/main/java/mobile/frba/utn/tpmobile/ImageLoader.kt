package mobile.frba.utn.tpmobile

import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageLoader{
    companion object {
        fun loadImageIn(imageView: ImageView, url: String){
            Picasso.get().load(url).fit().centerCrop().placeholder(R.drawable.image_placeholder).into(imageView)
        }
    }
}