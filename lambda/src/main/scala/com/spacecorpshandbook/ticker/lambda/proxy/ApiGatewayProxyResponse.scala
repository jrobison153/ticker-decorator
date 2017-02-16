package com.spacecorpshandbook.ticker.lambda.proxy

import beans.BeanProperty
import java.util.Map

/**
  * Required response structure for the AWS API Gateway
  */
class ApiGatewayProxyResponse {

  @BeanProperty
  var statusCode: String = _

  @BeanProperty
  var headers: Map[String, String] = _

  @BeanProperty
  var body: String = _

}