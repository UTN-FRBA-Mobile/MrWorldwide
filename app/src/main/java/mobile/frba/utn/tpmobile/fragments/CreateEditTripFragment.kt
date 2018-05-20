package mobile.frba.utn.tpmobile.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import mobile.frba.utn.tpmobile.R
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap
import android.provider.MediaStore
import android.content.Intent
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.Manifest.permission
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.*
import java.io.*
import java.util.jar.Manifest


class CreateEditTripFragment : Fragment() {

    var startDate: TextView? = null
    var finishDate: TextView? = null
    var calendar = Calendar.getInstance()
    var imageView: ImageView? = null
    private var buttonSelect: ImageButton? = null
    private var bitmap: Bitmap? = null
    private var destination: File? = null
    private var inputStreamImg: InputStream? = null
    private var imgPath: String? = null
    private val PICK_IMAGE_CAMERA = 1
    private val PICK_IMAGE_GALLERY = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_edit_trip, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startDate = view!!.findViewById(R.id.start_date)
        finishDate = view!!.findViewById(R.id.finish_date)

        startDate!!.text = "dd/mm/aaaa"
        finishDate!!.text = "dd/mm/aaaa"

        imageView = view!!.findViewById(R.id.trip_image)
        buttonSelect = view!!.findViewById(R.id.load_photo_button)

        buttonSelect!!.setOnClickListener(
            View.OnClickListener {
                selectImage()
        })


        setDateOnClick(startDate!!)
        setDateOnClick(finishDate!!)
    }

    private fun setDateOnClick(date: TextView) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView(date)
        }

        date.setOnClickListener {
            DatePickerDialog(activity,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateDateInView(date: TextView) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.text = sdf.format(calendar.time)
    }

    private fun selectImage() {
        try {
            val pm = activity!!.getPackageManager()
            val hasPerm = pm.checkPermission(android.Manifest.permission.CAMERA, activity!!.packageName)
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                val options = arrayOf<CharSequence>("Sacar Foto", "Seleccionar de la galería", "Cancelar")
                val builder = android.support.v7.app.AlertDialog.Builder(activity!!)
                builder.setTitle("Selecciona una opción")
                builder.setItems(options) { dialog, item ->
                    if (options[item] == "Sacar Foto") {
                        dialog.dismiss()
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, PICK_IMAGE_CAMERA)
                    } else if (options[item] == "Seleccionar de la galería") {
                        dialog.dismiss()
                        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
                    } else if (options[item] == "Cancelar") {
                        dialog.dismiss()
                    }
                }
                builder.show()
            } else
                Toast.makeText(this.context, "Camera Permission error", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this.context, "Camera Permission error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }


    public override fun onActivityResult(requestCode :Int, resultCode :Int, data :Intent) {
    super.onActivityResult(requestCode, resultCode, data);
    inputStreamImg = null
    if (requestCode == PICK_IMAGE_CAMERA) {
        try {
            var selectedImage = data.data;
            bitmap = data.getExtras().get("data") as Bitmap?;
            var bytes : ByteArrayOutputStream  = ByteArrayOutputStream();
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

            Log.e("Activity", "Pick from Camera::>>> ");

            var timeStamp :String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date());
            var environment = Environment.getExternalStorageDirectory()
            destination = File(environment.toString() + "/" + getString(R.string.app_name),"IMG$timeStamp.jpg" )
            var fo :FileOutputStream
            try {
                destination!!.createNewFile();
                fo = FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (e : FileNotFoundException) {
                e.printStackTrace();
            } catch (e : IOException) {
                e.printStackTrace();
            }

            imgPath = destination!!.getAbsolutePath();
            imageView!!.setImageBitmap(bitmap);

        } catch (e:Exception) {
            e.printStackTrace();
        }
    } else if (requestCode == PICK_IMAGE_GALLERY) {
        var selectedImage = data.data;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, selectedImage)
            var bytes : ByteArrayOutputStream = ByteArrayOutputStream();
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
            Log.e("Activity", "Pick from Gallery::>>> ");

            var filePathColumn : Array<String> = {MediaStore.Images.Media.DATA} as Array<String>
            var cursor : Cursor = activity!!.contentResolver.query(selectedImage,filePathColumn, null, null, null)
            cursor.moveToFirst()
            var columnIndex : Int= cursor.getColumnIndex(filePathColumn[0])
            imgPath = cursor.getString(columnIndex)
            cursor.close()

            destination = File(imgPath.toString());
            imageView!!.setImageBitmap(bitmap);

        } catch (e :Exception) {
            e.printStackTrace();
        }
    }
}


}
