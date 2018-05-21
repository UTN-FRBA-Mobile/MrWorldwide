package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.adapters.TravelerActivityListAdapter
import mobile.frba.utn.tpmobile.models.Photo
import mobile.frba.utn.tpmobile.models.Text
import mobile.frba.utn.tpmobile.models.Video
import org.joda.time.DateTime


class TravelersFragment : NavigatorFragment(R.id.action_travelers) {
	lateinit var recyclerView: RecyclerView


	override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
		var view = inflater.inflate(R.layout.travelers_view, parent, false)
		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val photo = Photo("http://4travellers.com.ar/wp-content/uploads/2015/08/Haulover-beach-4.jpg", DateTime.now(), "En la playa de Miami!")
		val text = Text("Wooooh, nos vamos de vaciones!",DateTime.now(),"Rumbo a MIAMEEEEE")
		val video = Video("Soy un video re cheto", "https://r2---sn-ab5szn7d.googlevideo.com/videoplayback?clen=8419541&key=yt6&gir=yes&sparams=clen%2Cdur%2Cei%2Cgir%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Crequiressl%2Csource%2Cexpire&mt=1526341008&requiressl=yes&expire=1526362746&ipbits=0&ei=Gh76Wv7MJIbahgaosZOICA&c=WEB&lmt=1526340564222585&mime=video%2F3gpp&signature=9273626DBF29C6598A0C6381634496C0EA62E3BF.C47DE8C0A6CEF8F8BF64A708101EEBDA011D1A7D&ms=au%2Crdu&source=youtube&mv=m&id=o-AEzU6n5X70dqlkJBC10j8DbvgWbcRVaNfMJ4Y5r67Hh-&initcwndbps=241250&pl=32&fvip=6&dur=846.645&itag=17&ip=2600%3A3c03%3A%3Af03c%3A91ff%3Afe7d%3Aa18e&mm=31%2C29&mn=sn-ab5szn7d%2Csn-ab5l6nzs&ratebypass=yes&title=INTENTANDO+DUCHAR+A+MIS+GATOS")
		recyclerView = getView()!!.findViewById(R.id.travelers_view_list)
		recyclerView.layoutManager=LinearLayoutManager(activity)
		recyclerView.adapter = TravelerActivityListAdapter(listOf(photo,text,video))
	}
}