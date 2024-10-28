package de.larshauke.swapi_android.networking.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateAdapter {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN)

    @FromJson
    fun fromJson(raw: String?): Date? {
        if (raw == null) return null
        return dateFormat.parse(raw)
    }

    @ToJson
    fun toJson(value: Date?) : String?{
        if (value == null) return null
        return value.toString()
    }
}