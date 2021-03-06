package mobile.frba.utn.tpmobile.fragments


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.maps.model.LatLng
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.activities.DateFormatter
import mobile.frba.utn.tpmobile.models.*
import mobile.frba.utn.tpmobile.singletons.LocationProvider
import mobile.frba.utn.tpmobile.singletons.Navigator
import mobile.frba.utn.tpmobile.singletons.RepoEvents
import mobile.frba.utn.tpmobile.singletons.RepoTrips
import org.joda.time.DateTime
import java.io.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet

class CreateEditEventFragment : NavigatorFragment(null) {
    private var date: TextView? = null
    private var calendar = Calendar.getInstance()
    private var imageView: ImageView? = null
    private var buttonSelect: ImageButton? = null
    private var bitmap: Bitmap? = null
    private var destination: File? = null
    private var photo: ByteArray? = null
    private var inputStreamImg: InputStream? = null
    private var imgPath: String? = null
    private var eventTitle: TextView? = null
    private var description: TextView? = null
    var trip: Trip? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_edit_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date = view.findViewById(R.id.event_date)

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            date!!.text = sdf.format(calendar.time)
        }

        imageView = view.findViewById(R.id.event_image)
        buttonSelect = view.findViewById(R.id.load_photo_button)
        eventTitle = view.findViewById(R.id.event_title)
        description = view.findViewById(R.id.event_description)

        buttonSelect!!.setOnClickListener({ selectImage() })

        if(eventAlreadyExits()) {
            val event = this.arguments!!.getSerializable("event") as Event

            when (event.eventType) {
                EventType.TEXT -> {
                    val textEvent = event as Text
                    description!!.text = textEvent.text
                }
                EventType.PHOTO -> {
                    val photoEvent = event as Photo
                    description!!.text = photoEvent.description

                    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                    StrictMode.setThreadPolicy(policy)
                    val bitmap = BitmapFactory.decodeStream(URL((photoEvent).url).openStream())
                    imageView!!.setImageBitmap(bitmap)

                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    photo = stream.toByteArray()
                }
                EventType.VIDEO -> {
                    val videoEvent = event as Video
                    description!!.text = videoEvent.description
                }
            }
            eventTitle!!.text = event.title
            date!!.text = android.text.format.DateFormat.format("dd/MM/yyyy", event.date!!.toDate())
        }

        date!!.setOnClickListener {
            DatePickerDialog(activity,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        onAcceptButtonClick()
        onCancelButtonClick()
    }

    private fun eventAlreadyExits() = this.arguments != null && this.arguments!!.containsKey("event")

    private fun selectImage() {
        try {
            val pm = activity!!.packageManager
            val hasPerm = pm.checkPermission(android.Manifest.permission.CAMERA, activity!!.packageName)
            if (hasPerm == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions((activity as FragmentActivity), arrayOf(android.Manifest.permission.CAMERA), 1)
            }
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                val options = arrayOf<CharSequence>("Sacar Foto", "Seleccionar de la galería", "Cancelar")
                val builder = android.support.v7.app.AlertDialog.Builder(activity!!)
                builder.setTitle("Selecciona una opción")
                builder.setItems(options) { dialog, item ->
                    when {
                        options[item] == "Sacar Foto" -> {
                            dialog.dismiss()
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, CreateEditTripFragment.PICK_IMAGE_CAMERA)
                        }
                        options[item] == "Seleccionar de la galería" -> {
                            dialog.dismiss()
                            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            startActivityForResult(pickPhoto, CreateEditTripFragment.PICK_IMAGE_GALLERY)
                        }
                        options[item] == "Cancelar" -> dialog.dismiss()
                    }
                }
                builder.show()
            }
        } catch (e: Exception) {
            Toast.makeText(this.context, "Camera Permission error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data==null) return
        super.onActivityResult(requestCode, resultCode, data)
        inputStreamImg = null
        if (requestCode == CreateEditTripFragment.PICK_IMAGE_CAMERA) {
            try {
                var selectedImage = data.data
                bitmap = data.extras.get("data") as Bitmap?
                var bytes = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
                photo = bytes.toByteArray()
                Log.e("Activity", "Pick from Camera::>>> ")

                var timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                var environment = Environment.getExternalStorageDirectory()
                destination = File(environment.toString() + "/" + getString(R.string.app_name), "IMG$timeStamp.jpg")
                var fo: FileOutputStream
                try {
                    destination!!.createNewFile()
                    fo = FileOutputStream(destination)
                    fo.write(bytes.toByteArray())
                    fo.close()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                imgPath = destination!!.absolutePath
                imageView!!.setImageBitmap(bitmap)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (requestCode == CreateEditTripFragment.PICK_IMAGE_GALLERY) {
            var selectedImage = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, selectedImage)
                var bytes = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
                photo = bytes.toByteArray()

                Log.e("Activity", "Pick from Gallery::>>> ")

                var filePathColumn: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
                var cursor: Cursor = activity!!.contentResolver.query(selectedImage, filePathColumn, null, null, null)
                cursor.moveToFirst()
                var columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                imgPath = cursor.getString(columnIndex)
                cursor.close()

                destination = File(imgPath.toString())
                imageView!!.setImageBitmap(bitmap)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun onAcceptButtonClick() {
        val acceptButton = view!!.findViewById<View>(R.id.accept_event)
        val spinner = ProgressBar(this.context)
        spinner.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        spinner.visibility = View.GONE

        val alertDialog = AlertDialog.Builder(this.context)
        alertDialog.setView(spinner)
        alertDialog.setCancelable(false)

        val incorrectEvent = AlertDialog.Builder(this.context)

        val spinnerDialog = alertDialog.create()
        spinnerDialog.setOnShowListener { _ -> spinner.visibility = View.VISIBLE }
        spinnerDialog.setOnCancelListener({ _ -> spinner.visibility = View.INVISIBLE })
        acceptButton.setOnClickListener {
            if (date?.text.isNullOrBlank()) {
                incorrectEvent.setMessage("Fecha invalida")
                incorrectEvent.show()
            } else {
                if (eventTitle?.text.isNullOrBlank()) {
                    incorrectEvent.setMessage("Nombre invalido")
                    incorrectEvent.show()
                } else {
                    var formatedDate = DateFormatter.getDateTimeFromStringWithSlash(date?.text.toString())
                    LocationProvider.requestSingleUpdate(context!!, { location ->
                        activity?.runOnUiThread {
                            if(eventAlreadyExits()) {
                                val event = this.arguments!!.getSerializable("event") as Event

                                when (event.eventType) {
                                    EventType.TEXT -> {
                                        val textEvent = this.arguments!!.getSerializable("event") as Text
                                        if(photo == null)
                                            UpdateTextFragment(textEvent, spinnerDialog)
                                        else {
                                            var newEvent = Photo("",
                                                    textEvent.likes,
                                                    eventTitle!!.text.toString(),
                                                    DateFormatter.getDateTimeFromStringWithSlash(date?.text.toString()),
                                                    description!!.text.toString(),
                                                    textEvent.geoLocation,
                                                    textEvent.id,
                                                    textEvent.userId,
                                                    textEvent.tripId)
                                            spinnerDialog.show()
                                            RepoEvents.savePhotoThenUpdateEvent(photo!!, newEvent, {
                                                cancelSpinnerDialogAndReturnToPreviousfragment(spinnerDialog)
                                            })
                                        }
                                    }
                                    EventType.PHOTO -> UpdatePhotoFragment(spinnerDialog)
                                    EventType.VIDEO -> {
                                        val videoEvent = event as Video
                                        description!!.text = videoEvent.description
                                    }
                                }
                            }
                            else {
                                CreateEvent(formatedDate, Coordinate.fromLatLng(location), spinnerDialog)
                            }
                        }
                    })

                }
            }
        }
    }

    private fun UpdateTextFragment(textEvent: Text, spinnerDialog: AlertDialog) {

        textEvent.title = eventTitle!!.text.toString()
        textEvent.date = DateFormatter.getDateTimeFromStringWithSlash(date?.text.toString())
        textEvent.text = description!!.text.toString()
        RepoEvents.updateEvent(textEvent, {
            cancelSpinnerDialogAndReturnToPreviousfragment(spinnerDialog)
        })
    }

    private fun UpdatePhotoFragment(spinnerDialog: AlertDialog) {
        val photoEvent = this.arguments!!.getSerializable("event") as Photo

        photoEvent.title = eventTitle!!.text.toString()
        photoEvent.date = DateFormatter.getDateTimeFromStringWithSlash(date?.text.toString())
        photoEvent.description = description!!.text.toString()


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val eventPhotoBitmap = BitmapFactory.decodeStream(URL(photoEvent.url).openStream())
        val imageViewBitmap = imageView!!.drawingCache

        if (eventPhotoBitmap != imageViewBitmap) {
            RepoEvents.savePhotoThenUpdateEvent(photo!!, photoEvent, {
                cancelSpinnerDialogAndReturnToPreviousfragment(spinnerDialog)
            })
        } else {
            RepoEvents.updateEvent(photoEvent, {
                cancelSpinnerDialogAndReturnToPreviousfragment(spinnerDialog)
            })
        }
    }

    private fun CreateEvent(formatedDate: DateTime, location: Coordinate, spinnerDialog: AlertDialog) {
        var event : Event
        if (photo == null) {
            event = Text(description?.text.toString(), HashSet(0), eventTitle?.text.toString(), formatedDate, location, null, null, trip?.id)
            spinnerDialog.show()
            RepoEvents.addEvent(event, {
                spinnerDialog.cancel()
                val fragment = BitacoraFragment()
                RepoTrips.getTrip(this, trip?.id!!).invoke { newTrip ->
                    fragment.trip = newTrip
                    Navigator.navigateTo(fragment)
                }
            })
        } else {
            event = Photo("", HashSet(0), eventTitle?.text.toString(), formatedDate, description?.text.toString(), location, null, null, trip?.id)
            spinnerDialog.show()
            RepoEvents.savePhotoAndThenAddEvent(photo!!, event, {
                spinnerDialog.cancel()
                val fragment = BitacoraFragment()
                RepoTrips.getTrip(this, trip?.id!!).invoke { newTrip ->
                    fragment.trip = newTrip
                    Navigator.navigateTo(fragment)
                }
            })
        }
    }

    private fun cancelSpinnerDialogAndReturnToPreviousfragment(spinnerDialog: AlertDialog) {
        spinnerDialog.cancel()
        Navigator.navigateTo(BitacoraFragment())
    }

    private fun onCancelButtonClick() {
        val cancelButton = view!!.findViewById<View>(R.id.cancel_event)
        cancelButton.setOnClickListener { Navigator.navigateTo(BitacoraFragment()) }
    }
}
