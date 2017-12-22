/*
 * Copyright 2017 Students
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.http.sample.app

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.stream.{ ActorMaterializer, Materializer }
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import scala.concurrent.ExecutionContextExecutor
import spray.json.DefaultJsonProtocol

case class Random(n: Double)

trait Protocols extends DefaultJsonProtocol {
  implicit val RandomFormat = jsonFormat1(Random.apply)
}

trait Service extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val logger: LoggingAdapter

  val routes = {
    logRequestResult("sample-service") {
      pathPrefix("random") {
        get {
          complete {
            Random(math.random)
          }
        }
      }
    }
  }
}

object SampleService extends App with Service {
  override implicit val system       = ActorSystem()
  override implicit val executor     = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  Http().bindAndHandle(routes,
                       config.getString("http.interface"),
                       config.getString("http.port").toInt)
}
