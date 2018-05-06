package mobile.frba.utn.tpmobile.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import mobile.frba.utn.tpmobile.ImageLoader
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.Photo
import mobile.frba.utn.tpmobile.models.Text

class BitacoraListAdapter(var items: List<Event>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        return when(viewType){
            0 -> TextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.text_item, parent, false))
            1 -> ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false))
            else -> throw RuntimeException("Invalid viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BitacoraViewHolder).bind(items[position])
    }

    override fun getItemViewType(position: Int): Int = items[position].viewType

    abstract class BitacoraViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        abstract fun bind(event: Event)
    }

    class TextViewHolder(itemView: View) : BitacoraViewHolder(itemView) {

        val titleView :TextView = itemView.findViewById(R.id.text_item_title)
        val dateView :TextView = itemView.findViewById(R.id.text_item_date)
        val textView :TextView = itemView.findViewById(R.id.text_item_text)

        override fun bind(event: Event) = with(event as Text) {
                titleView.text = title
                dateView.text = date.toString()
                textView.text = text
            }
    }

    class ImageViewHolder(itemView: View): BitacoraViewHolder(itemView){
        val photoView : ImageView = itemView.findViewById(R.id.image_item_photo)
        val dateView : TextView = itemView.findViewById(R.id.image_item_date)

        override fun bind(event: Event) = with(event as Photo){
            ImageLoader.loadImageIn(photoView, url)
            dateView.text=date.toString()
        }

    }
}