package com.spacecorpshandbook.ticker.io.db

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  */
@RunWith(classOf[JUnitRunner])
class MongoConnectionITest extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  behavior of "A MongoConnection"

  it should "return a connection to the default database" in {

  }

}
