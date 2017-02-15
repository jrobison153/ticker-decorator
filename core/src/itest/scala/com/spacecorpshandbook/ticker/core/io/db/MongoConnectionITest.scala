package com.spacecorpshandbook.ticker.core.io.db

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class MongoConnectionITest extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  behavior of "A MongoConnection"

  it should "return a connection to the default database" in {

    val database = MongoConnection.getDefaultDatabase

  }

}
