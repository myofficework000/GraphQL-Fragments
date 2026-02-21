package com.code4galaxy.graphqlspacex.data.graphql.adapters

import com.apollographql.apollo.api.Adapter
import com.apollographql.apollo.api.CustomScalarAdapters
import com.apollographql.apollo.api.json.JsonReader
import com.apollographql.apollo.api.json.JsonWriter
import java.util.UUID

object UuidAdapter : Adapter<UUID> {
    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters): UUID {
        val uuidString = reader.nextString()
        return UUID.fromString(uuidString)
    }

    override fun toJson(writer: JsonWriter, customScalarAdapters: CustomScalarAdapters, value: UUID) {
        writer.value(value.toString())
    }
}
