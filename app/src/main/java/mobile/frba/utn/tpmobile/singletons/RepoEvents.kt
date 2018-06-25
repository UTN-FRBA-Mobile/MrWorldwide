package mobile.frba.utn.tpmobile.singletons

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import mobile.frba.utn.tpmobile.Constants
import mobile.frba.utn.tpmobile.activities.DateFormatter
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.Photo
import org.joda.time.DateTime
import org.json.JSONObject

/**
 * Created by Gustavo on 6/24/18.
 */
object RepoEvents {
    var events: MutableList<Event> = ArrayList()
    var backUrl = "http://10.0.2.2:3000"
    var userId = "Agustin Vertebrado"
    private val gson = GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, JsonSerializer<DateTime> { date, _, _ -> JsonPrimitive(DateFormatter.format(date)) })
            .create()


    fun addEvent(event: Event, callback: () -> Unit) {
        RepoEvents.events.add(RepoEvents.events.lastIndex + 1, event)
        "${RepoEvents.backUrl}/events/${RepoEvents.userId}"
                .httpPost()
                .body(RepoEvents.gson.toJson(event))
                .header(Pair("Content-Type", "application/json"))
                .response({ _, _, result ->
                    println(result)
                    callback.invoke()
                })
    }

    fun savePhotoAndThenAddEvent(photo: ByteArray, event: Photo, callback: () -> Unit) {
        "https://api.imgur.com/3/image"
            .httpPost()
            .header(Pair("Authorization", Constants.clientAuth))
            .body(photo)
            .responseJson({_,response, result ->
                event.url = JSONObject((result as Result.Success).value.content).getJSONObject("data").getString("link")
                addEvent(event, callback)
            })
    }
}