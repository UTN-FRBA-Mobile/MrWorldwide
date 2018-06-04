package mobile.frba.utn.tpmobile.fragments

import android.widget.TextView
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import mobile.frba.utn.tpmobile.R


class SecondFragment : Fragment() {
    // Store instance variables
    private var title: String? = null
    private var page: Int = 0

    // Store instance variables based on arguments passed
    /*fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        page = getArguments().getInt("someInt", 0)
        title = getArguments().getString("someTitle")
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvLabel = view!!.findViewById(R.id.tvLabel) as TextView
        page = getArguments()!!.getInt("someInt", 0)
        title = getArguments()!!.getString("someTitle")
        tvLabel.text = page.toString() + " -- " + title
    }

    companion object {

        // newInstance constructor for creating fragment with arguments
        fun newInstance(page: Int, title: String): FirstFragment {
            val fragmentFirst = FirstFragment()
            val args = Bundle()
            args.putInt("someInt2", page)
            args.putString("someTitle2", title)
            fragmentFirst.setArguments(args)
            return fragmentFirst
        }
    }
}
