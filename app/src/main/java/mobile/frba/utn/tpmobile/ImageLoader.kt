package mobile.frba.utn.tpmobile

import android.widget.ImageView

class ImageLoader{
    companion object {
        fun loadImageIn(imageView: ImageView, url: String){
            //TODO: Implement this
            imageView.setImageResource(R.drawable.trip_photo_example)
        }
    }

}