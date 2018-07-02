package mobile.frba.utn.tpmobile.models

import com.google.gson.FieldAttributes
import com.google.gson.ExclusionStrategy


class CustomExclusionStrategies : ExclusionStrategy {
    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.name.equals("date")
    }

    override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return false
    }
}