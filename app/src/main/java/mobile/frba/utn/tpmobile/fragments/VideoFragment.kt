package mobile.frba.utn.tpmobile.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import mobile.frba.utn.tpmobile.R
import android.media.MediaPlayer.OnPreparedListener
import android.util.Log
import android.net.Uri
import android.app.ProgressDialog
import android.widget.MediaController
import android.widget.VideoView
import mobile.frba.utn.tpmobile.helpers.CustomController


class VideoFragment : Fragment(){
	var myVideoView:VideoView? = null
	private var position = 0
	private var progressDialog:ProgressDialog? = null
	private var mediaControls: MediaController? = null

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Defines the xml file for the fragment
        var view = inflater.inflate(R.layout.video_view, parent, false)
	 	myVideoView = view.findViewById(R.id.video_view) as VideoView

		//set the media controller buttons
		if (mediaControls == null) {
			mediaControls = CustomController(myVideoView!!.context, myVideoView!!)
		}

		// create a progress bar while the video file is loading
		progressDialog = ProgressDialog(view.context)
		// set a title for the progress bar
		progressDialog!!.setTitle("JavaCodeGeeks Android Video View Example")
		// set a message for the progress bar
		progressDialog!!.setMessage("Loading...")
		//set the progress bar not cancelable on users' touch
		progressDialog!!.setCancelable(false)
		// show the progress bar
		progressDialog!!.show()

		try {
			//set the media controller in the VideoView
			myVideoView!!.setMediaController(mediaControls)

			//set the uri of the video to be played
			myVideoView!!.setVideoURI( Uri.parse(arguments!!.getString("videoURL")))

		} catch (e: Exception) {
			Log.e("Error", e.message)
			e.printStackTrace()
		}


		myVideoView!!.requestFocus()
		//we also set an setOnPreparedListener in order to know when the video file is ready for playback
		myVideoView!!.setOnPreparedListener(OnPreparedListener {
			// close the progress bar and play the video
			progressDialog!!.dismiss()
			//if we have a position on savedInstanceState, the video playback should start from here
			myVideoView!!.seekTo(position)
			if (position === 0) {
				myVideoView!!.start()
			} else {
				//if we come from a resumed activity, video playback will be paused
				myVideoView!!.pause()
			}
		})
		return view
	}
}

fun newVideoFragment(videoURL: String): VideoFragment {
	val myFragment = VideoFragment()
	val args = Bundle()
	args.putString("videoURL",videoURL)
	myFragment.arguments = args
	return myFragment
}