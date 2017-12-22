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

import java.util.UUID

import ExchangeSubscription.SubscriptionId

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
abstract class ExchangeSubscription {

  def ticker: String

  //TODO: proper logging and error handling
  def onFailure(errorCode: Option[Int], ex: Exception): Unit =
    println(s"$errorCode: " + ex.getMessage)
  def onMessage(msg: String): Unit
}

object ExchangeSubscription {

  type SubscriptionId = String

}
