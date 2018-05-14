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
        val photo = Photo("asd", DateTime.now())
        val text = Text("Soy un titulo de relleno",DateTime.now(),"Soy un texto de relleno")
        recyclerView = getView()!!.findViewById(R.id.bitacora_list)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        recyclerView.adapter = BitacoraListAdapter(listOf(photo,photo,photo))
    }
}