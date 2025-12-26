package br.com.neckel.interface.http.serialization

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import akka.util.ByteString
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule

trait JsonSupport {

  implicit val objectMapper: ObjectMapper =
    new ObjectMapper()
      .registerModule(DefaultScalaModule)
      .registerModule(new JavaTimeModule())

  implicit def jacksonUnmarshaller[A: Manifest]: FromEntityUnmarshaller[A] =
    Unmarshaller.byteStringUnmarshaller
      .forContentTypes(ContentTypes.`application/json`)
      .map(data => objectMapper.readValue(data.toArray, manifest.runtimeClass).asInstanceOf[A])

  implicit def jacksonMarshaller[A <: AnyRef]: ToEntityMarshaller[A] =
    Marshaller.withFixedContentType(ContentTypes.`application/json`) { value =>
      HttpEntity(ContentTypes.`application/json`, ByteString(objectMapper.writeValueAsBytes(value)))
    }


}
