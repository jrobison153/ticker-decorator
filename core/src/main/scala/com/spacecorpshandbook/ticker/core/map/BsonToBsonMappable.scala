package com.spacecorpshandbook.ticker.core.map


import com.spacecorpshandbook.ticker.core.model.BsonMappable
import org.bson.{BsonInt32, BsonString}
import org.mongodb.scala.Document
import org.mongodb.scala.bson.{BsonDouble, BsonValue}

import scala.reflect.runtime.{universe => ru}

/**
  * Map any BSON document to scala objects, bold!
  */
object BsonToBsonMappable {

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
  }

  private def mapValueToObjectInstanceField(fieldValue: BsonValue, fieldMirror: ru.FieldMirror) = {

    if (fieldValue.isString) {

      fieldMirror.set(fieldValue.asInstanceOf[BsonString].getValue)
    }
    else if (fieldValue.isDecimal128) {

      fieldMirror.set(BigDecimal(fieldValue.asInstanceOf[BsonString].getValue))
    }
    else if (fieldValue.isInt32) {

      fieldMirror.set(fieldValue.asInstanceOf[BsonInt32].getValue)
    }
    else if(fieldValue.isDouble) {

      fieldMirror.set(fieldValue.asInstanceOf[BsonDouble].getValue)
    }
    else {

      println("Unknown field type in BSON Document' " + fieldValue.toString + "'. Value cannot be mapped until mapper is updated")
    }
  }
}
