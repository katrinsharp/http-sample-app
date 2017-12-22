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

import java.io.IOException
import java.util.UUID

import okhttp3._

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
trait ExchangeClient {

  import ExchangeSubscription._

  def baseUrl: String

  protected def subscribe(exSub: ExchangeSubscription): SubscriptionId = {
    val subscriptionId: SubscriptionId = UUID.randomUUID().toString
    val request                        = new Request.Builder().url(baseUrl + exSub.ticker).tag(subscriptionId).build
    client
      .newCall(request)
      .enqueue(new Callback {
        override def onFailure(call: Call, e: IOException): Unit = exSub.onFailure(None, e)
        override def onResponse(call: Call, response: Response): Unit =
          response.isSuccessful match {
            case true => exSub.onMessage(response.body().string())
            case _    => exSub.onFailure(Some(response.code()), new Exception(response.message()))
          }
      })
    subscriptionId
  }

  def cancel(subId: SubscriptionId) = {

    import scala.collection.JavaConversions._

    def cancelAndRemove(calls: List[Call]) =
      calls.foreach(call => {
        if (call.request().tag == subId) {
          call.cancel()
        }
      })

    cancelAndRemove(client.dispatcher().queuedCalls().toList)
    cancelAndRemove(client.dispatcher().runningCalls().toList)
  }

  //TODO: reuse 1 OkHttp client
  private val client = new OkHttpClient
}
