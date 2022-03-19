package it.adami.blog.model.serializer

import com.fasterxml.jackson.core.{JsonGenerator, JsonParser}
import com.fasterxml.jackson.databind.{DeserializationContext, SerializerProvider}
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import it.adami.blog.model.Gender

class GenderJsonSerializer extends StdSerializer[Gender](classOf[Gender]) {

  override def serialize(value: Gender, gen: JsonGenerator, provider: SerializerProvider): Unit = {
    gen.writeString(value.name)
  }
}

class GenderJsonDeserializer extends StdDeserializer[Gender](classOf[Gender]) {

  override def deserialize(p: JsonParser, ctxt: DeserializationContext): Gender =
    Gender(p.getText)
}
