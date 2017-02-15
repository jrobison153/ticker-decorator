package com.spacecorpshandbook.ticker.core.model

import scala.reflect.runtime.universe.TypeTag
import scala.reflect.runtime.{universe => ru}

/**
  * A class that can be mapped to and from a BSON Document.
  */
abstract class BsonMappable[T <: BsonMappable[_]: TypeTag] {

  def mappableType = ru.typeOf[T]

  BsonMappable.mappableFields = {

    if (!BsonMappable.mappableFields.contains(mappableType.hashCode)) {
      BsonMappable.mappableFields + (mappableType.hashCode ->
        mappableType.decls.collect {
          case m: ru.MethodSymbol if m.isGetter => m

        }.toSeq)
    }
    else {
      BsonMappable.mappableFields
    }
  }

  /**
    *
    * @return Map containing hash key of the mappable type to a sequence of method symbols
    */
  def getMappableFields = BsonMappable.mappableFields

  /**
    * @return Seq[ru.MethodSymbol] all mappable fields for this concrete type
    */
  def getMyTypeMappableFields = BsonMappable.mappableFields(mappableType.hashCode)
}

object BsonMappable {

  var mappableFields: Map[Int, Seq[ru.MethodSymbol]] = Map.empty
}
