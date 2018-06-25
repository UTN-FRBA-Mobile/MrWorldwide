package mobile.frba.utn.tpmobile.adapters

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import mobile.frba.utn.tpmobile.ImageLoader
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.activities.DateFormatter
import mobile.frba.utn.tpmobile.fragments.newVideoView
import mobile.frba.utn.tpmobile.fragments.updateVideoView
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.Photo
import mobile.frba.utn.tpmobile.models.Text
import mobile.frba.utn.tpmobile.models.Video

class BitacoraListAdapter(var items: List<Event>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> TextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bitacora_activity_item, parent, false))
            1 -> ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bitacora_activity_item, parent, false))
            2 -> VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bitacora_activity_item, parent, false))
            else -> throw RuntimeException("Invalid viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BitacoraViewHolder).bind(items[position])
    }

    override fun getItemViewType(position: Int): Int = items[position].eventType.viewType

    abstract class BitacoraViewHolder(itemView: View) : AdapterWithSharedButtonHolder , RecyclerView.ViewHolder(itemView) {
        abstract fun bind(event: Event)

    }

    class TextViewHolder(itemView: View) : BitacoraViewHolder(itemView) {

        var titleView: TextView
        var dateView: TextView
        var textView: TextView
        var userView : TextView

        init {
            val frame: FrameLayout = itemView.findViewById(R.id.bitacora_activity_content)
            val textContent = (itemView.context as FragmentActivity).layoutInflater.inflate(R.layout.text_item, (itemView as ViewGroup), false)
            frame.addView(textContent)
            titleView = itemView.findViewById(R.id.text_item_title)
            dateView = itemView.findViewById(R.id.text_item_date)
            textView = itemView.findViewById(R.id.text_item_text)
            userView = itemView.findViewById(R.id.user_id)

       }

        override fun bind(event: Event) = with(event as Text) {
            titleView.text = title
            dateView.text = DateFormatter.format(date)
            textView.text = text
            userView.text = userId
            activatedSharedButton(event,itemView)

        }
    }

    class ImageViewHolder(itemView: View) : BitacoraViewHolder(itemView) {
        var photoView: ImageView
        var dateView: TextView
        var descriptionView: TextView
        var userView : TextView
        var titleView: TextView

        init {
            val frame: FrameLayout = itemView.findViewById(R.id.bitacora_activity_content)
            val imageContent = (itemView.context as FragmentActivity).layoutInflater.inflate(R.layout.image_item, (itemView as ViewGroup), false)
            frame.addView(imageContent)
            photoView = itemView.findViewById(R.id.image_item_photo)
            dateView = itemView.findViewById(R.id.image_item_date)
            descriptionView = itemView.findViewById(R.id.image_item_text)
            userView = itemView.findViewById(R.id.user_id)
            titleView = itemView.findViewById(R.id.title)
        }

        override fun bind(event: Event) = with(event as Photo) {
            ImageLoader.loadImageIn(photoView, url)
            dateView.text = DateFormatter.format(date)
            descriptionView.text = description
            userView.text = event.userId
            activatedSharedButton(event,itemView)
            titleView.text = title
        }

    }

    class VideoViewHolder(itemView: View): BitacoraViewHolder(itemView){

        var videoContainer : View = newVideoView(itemView)
        var videoView : VideoView
        var videoItem : View
        var userView : TextView
        var dateView : TextView
        init{
            val frame :FrameLayout = itemView.findViewById(R.id.bitacora_activity_content)
            videoItem = (itemView.context as FragmentActivity).layoutInflater.inflate(R.layout.video_item,(itemView as ViewGroup),false)
            frame.addView(videoItem)
            val frameVideo = videoItem.findViewById<FrameLayout>(R.id.video_container)
            frameVideo.addView(videoContainer)
            videoView = videoContainer.findViewById(R.id.video_view)
            userView = videoItem.findViewById(R.id.user_id)
            dateView = itemView.findViewById(R.id.video_item_date)

        }
        override fun bind(event: Event): Unit = with(event as Video){
            updateVideoView(url ,videoView)
            userView.text = event.userId
            dateView.text = DateFormatter.format(date)
            activatedSharedButton(event,itemView)
        }
    }
}