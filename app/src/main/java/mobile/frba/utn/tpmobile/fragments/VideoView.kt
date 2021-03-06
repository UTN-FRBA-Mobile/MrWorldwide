package mobile.frba.utn.tpmobile.fragments

import android.net.Uri
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.helpers.CustomController


fun newVideoView(itemView : View): View {
	var view = (itemView.context as FragmentActivity).layoutInflater.inflate(R.layout.video_view, (itemView as ViewGroup), false)
	var myVideoView = view.findViewById(R.id.video_view) as VideoView
	var	mediaControls = CustomController(myVideoView.context, myVideoView)


	try {
		//set the media controller in the VideoView
		myVideoView.setMediaController(mediaControls)

	} catch (e: Exception) {
		Log.e("Error", e.message)
		e.printStackTrace()
	}

	return view

}

fun updateVideoView(videoURL: String, videoView: VideoView) = videoView.setVideoURI(Uri.parse(videoURL))