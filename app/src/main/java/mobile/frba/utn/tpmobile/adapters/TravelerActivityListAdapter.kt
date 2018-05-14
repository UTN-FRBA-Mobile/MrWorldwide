package mobile.frba.utn.tpmobile.adapters

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.newVideoView
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.Photo
import mobile.frba.utn.tpmobile.models.Text
import mobile.frba.utn.tpmobile.models.Video

class TravelerActivityListAdapter(var items: List<Event>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        return when(viewType){
            0 -> TextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.traveler_activity_item, parent, false))
            1 -> ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.traveler_activity_item, parent, false))
            2 -> VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.traveler_activity_item, parent, false))
            else -> throw RuntimeException("Invalid viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TravelersViewHolder).bind(items[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
         (recyclerView.context as FragmentActivity).supportFragmentManager


    }
    override fun getItemViewType(position: Int): Int = items[position].viewType

    abstract class TravelersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        abstract fun bind(event: Event)
    }

    class TextViewHolder(itemView: View) : TravelersViewHolder(itemView) {

        init{
            val frame = itemView.findViewById<FrameLayout>(R.id.traveler_activity_content)
            val text = (itemView.context as FragmentActivity).layoutInflater.inflate(R.layout.text_item,(itemView as ViewGroup),false)
            frame.addView(text)
        }

        override fun bind(event: Event) = with(event as Text) {
        }
    }

    class ImageViewHolder(itemView: View): TravelersViewHolder(itemView){
        init{
            val frame = itemView.findViewById<FrameLayout>(R.id.traveler_activity_content)
            val img = (itemView.context as FragmentActivity).layoutInflater.inflate(R.layout.image_item,(itemView as ViewGroup),false)
            frame.addView(img)
        }
        override fun bind(event: Event) = with(event as Photo){
        }

    }
    class VideoViewHolder(itemView: View): TravelersViewHolder(itemView){

        init{
            val frame = itemView.findViewById<FrameLayout>(R.id.traveler_activity_content)
            val videoItem = (itemView.context as FragmentActivity).layoutInflater.inflate(R.layout.video_item,(itemView as ViewGroup),false)
            frame.addView(videoItem)
            val videoView = newVideoView(videoItem)
            val frameVideo = videoItem.findViewById<FrameLayout>(R.id.video_container)
            frameVideo.addView(videoView)
        }
        override fun bind(event: Event): Unit = with(event as Video){
         }
    }
}