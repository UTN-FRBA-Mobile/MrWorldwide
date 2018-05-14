package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.adapters.BitacoraListAdapter
import mobile.frba.utn.tpmobile.models.Photo
import mobile.frba.utn.tpmobile.models.Text
import org.joda.time.DateTime

class BitacoraFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var adapter :BitacoraListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bitacora_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photo = Photo("http://4travellers.com.ar/wp-content/uploads/2015/08/Haulover-beach-4.jpg", DateTime.now(), "En la playa de Miami!")
        val text = Text("Wooooh, nos vamos de vaciones!",DateTime.now(),"Rumbo a MIAMEEEEE")
        recyclerView = getView()!!.findViewById(R.id.bitacora_list)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        recyclerView.adapter = BitacoraListAdapter(listOf(photo,photo,photo))
    }
}