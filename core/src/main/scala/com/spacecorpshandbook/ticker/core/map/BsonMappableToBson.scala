package com.spacecorpshandbook.ticker.core.map

import java.time.{LocalDateTime, ZoneId}

import com.spacecorpshandbook.ticker.core.model.BsonMappable
import org.bson.BsonDateTime
import org.mongodb.scala.Document
import org.mongodb.scala.bson.BsonDecimal128

import scala.reflect.runtime.{universe => ru}

/**
  * Map BsonMappabale types to a BsonDocument
  */
object BsonMappableToBson {

  val runtimeMirror = ru.runtimeMirror(BsonToBsonMappable.getClass.getClassLoader)

  def map(sourceMappable: BsonMappable[_ <: BsonMappable[_]]): Document = {

    var document: Document = Document()

    val mappableFields = sourceMappable.getMyTypeMappableFields

    val instanceMirror = runtimeMirror.reflect(sourceMappable)

    mappableFields.foreach(field => {

      val fieldTermSymbol = sourceMappable.mappableType.decl(ru.TermName(field.name.toString)).asTerm
      val fieldMirror = instanceMirror.reflectField(fieldTermSymbol)
      val fieldName: String = fieldTermSymbol.name.toString

      fieldMirror.get match {
        case strValue: String => document = document + (fieldName -> strValue)

        case decValue: BigDecimal => document = document + (fieldName -> BsonDecimal128(decValue))

        case dateTimeValue : LocalDateTime =>
          document = document +
            (fieldName -> new BsonDateTime(dateTimeValue.atZone(ZoneId.of("UTC")).toInstant.toEpochMilli))

        case _ => println(s"Unhandled type '$fieldMirror', cannot map to BsonMappable until mapper is updated")
      }
    })

    document
  }
}
