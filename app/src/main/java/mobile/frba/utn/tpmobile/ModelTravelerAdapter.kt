package mobile.frba.utn.tpmobile

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView



class ModelTravelerAdapter(val items: MutableList<ModelTraveler>) : RecyclerView.Adapter<ModelViewHolder>() {

    override fun onBindViewHolder(holder: ModelViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ModelViewHolder {
        val height = parent?.measuredHeight?.div(8)
        val width = parent?.measuredWidth
        val view: View? =  LayoutInflater.from(parent?.context).inflate(R.layout.traveler_list_item, parent, false)
        view?.layoutParams = RecyclerView.LayoutParams(width!!, height!!)
        return  ModelViewHolder(view!!)
    }

}

class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView: TextView = itemView.findViewById(R.id.text_view)

    fun bind(model: ModelTraveler) {
        textView.text = model.nombre
    }
}