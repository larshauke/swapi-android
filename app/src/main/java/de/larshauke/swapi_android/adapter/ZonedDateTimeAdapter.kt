package de.larshauke.swapi_android.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME

class ZonedDateTimeAdapter {

    @FromJson
    fun fromRaw(raw: String?): ZonedDateTime? {
        if (raw == null) return null
        return ZonedDateTime.parse(raw, ISO_OFFSET_DATE_TIME)
    }

    @ToJson
    fun toRaw(value: ZonedDateTime?): String? {
        if (value == null) return null

        return ISO_OFFSET_DATE_TIME.format(value)
    }
}