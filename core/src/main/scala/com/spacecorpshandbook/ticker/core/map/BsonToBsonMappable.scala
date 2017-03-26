package com.spacecorpshandbook.ticker.core.map


import java.time._

import com.spacecorpshandbook.ticker.core.model.BsonMappable
import org.bson.{BsonInt32, BsonString}
import org.mongodb.scala.Document
import org.mongodb.scala.bson.{BsonDouble, BsonValue}

import scala.reflect.runtime.{universe => ru}

/**
  * Map any BSON document to scala objects, bold!
  */
object BsonToBsonMappable {

  val PRIMARY_KEY_DB = "_id"
  val PRIMARY_KEY_BSON_MAPPABLE = "id"

  val runtimeMirror = ru.runtimeMirror(BsonToBsonMappable.getClass.getClassLoader)

  /**
    *
    * Map values from a BSON docuemnt to an object. Currently supports mapping the following BSON types
    *
    * BSON String maps to Scala String
    * BSON Int32 maps to Scala Int
    * BSON decimal128 maps to Scala BigDecimal
    *
    * @param fromDoc - BSON document to map to object
    * @param toObj   - Target object to be updated with values from BSON document
    */
  def map(fromDoc: Document, toObj: BsonMappable[_ <: BsonMappable[_]]) = {

    val mappableFields = toObj.getMyTypeMappableFields

    val fieldsPresentInDoc = mappableFields.filter((field: ru.MethodSymbol) => fromDoc.containsKey(field.name.toString))

    val instanceMirror = runtimeMirror.reflect(toObj)

    fieldsPresentInDoc.foreach(field => {

      val fieldTermSymbol = toObj.mappableType.decl(ru.TermName(field.name.toString)).asTerm
      val fieldMirror = instanceMirror.reflectField(fieldTermSymbol)

      fromDoc.get(field.name.toString) match {

        case Some(fieldValue) => mapValueToObjectInstanceField(fieldValue, fieldMirror)
        case None => println("Document did not contain value for key: '" + field.name.toString + "'")
      }
    })

    // may need to specially handle mapping of _id to id because we cannot annotate _id with @BeanProperty
    // in our BsonMappable and _id is the default id field in the database
    fromDoc.get(PRIMARY_KEY_DB) match {

      case Some(idValue) =>
        val fieldTermSymbol = toObj.mappableType.decl(ru.TermName(PRIMARY_KEY_BSON_MAPPABLE)).asTerm
        val fieldMirror = instanceMirror.reflectField(fieldTermSymbol)

        if (idValue.isObjectId) {

          fieldMirror.set(idValue.asObjectId.getValue.toHexString)
        }
      case None =>
    }
  }

  private def mapValueToObjectInstanceField(fieldValue: BsonValue, fieldMirror: ru.FieldMirror) = {

    if (fieldValue.isString) {

      fieldMirror.set(fieldValue.asInstanceOf[BsonString].getValue)
    }
    else if (fieldValue.isDecimal128) {

      fieldMirror.set(new BigDecimal(fieldValue.asDecimal128().getValue.bigDecimalValue))
    }
    else if (fieldValue.isInt32) {

      fieldMirror.set(fieldValue.asInstanceOf[BsonInt32].getValue)
    }
    else if (fieldValue.isDouble) {

      fieldMirror.set(fieldValue.asInstanceOf[BsonDouble].getValue)
    }
    else if (fieldValue.isObjectId) {

      fieldMirror.set(fieldValue.asObjectId.getValue.toHexString)
    }
    else if (fieldValue.isDateTime) {

      val secondsSinceEpoch = fieldValue.asDateTime.getValue
      val instantSinceEpoch = Instant.ofEpochMilli(secondsSinceEpoch)
      val zonedDateTime = instantSinceEpoch.atZone(ZoneId.of("UTC"))
      val date = zonedDateTime.toLocalDateTime

      fieldMirror.set(date)
    }
    else {

      println("Unknown field type in BSON Document' " + fieldValue.toString + "'. Value cannot be mapped until mapper is updated")
    }
  }
}
