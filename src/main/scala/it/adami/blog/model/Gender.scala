package it.adami.blog.model

import com.typesafe.scalalogging.LazyLogging
import com.fasterxml.jackson.databind.annotation.{JsonDeserialize, JsonSerialize}
import it.adami.blog.model.serializer.{GenderJsonDeserializer, GenderJsonSerializer}

@JsonSerialize(`using` = classOf[GenderJsonSerializer])
@JsonDeserialize(`using` = classOf[GenderJsonDeserializer])
trait Gender {
  def name: String

  override def toString: String = name
}

object Gender extends LazyLogging {

  object Male extends Gender {
    override val name: String = "MALE"
  }

  object Female extends Gender {
    override val name: String = "FEMALE"
  }

  def apply(string: String): Gender = string match {
    case Male.name => Male
    case Female.name => Female
    case other =>
      val message = "Illegal value $other for  Gender.apply"
      logger.error(message)
      throw new IllegalArgumentException(message)
  }

}