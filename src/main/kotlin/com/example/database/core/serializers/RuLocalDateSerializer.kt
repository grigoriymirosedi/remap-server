package com.example.database.core.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Serializer(forClass = LocalDate::class)
class RuLocalDateSerializer : KSerializer<LocalDate> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    private val ruFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(ruFormatter))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), ruFormatter)
    }
}