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

package com.http.sample.app.gdax

import com.http.sample.app.ExchangeSubscription.SubscriptionId
import com.http.sample.app.gdax.GdaxTickerTypes.GdaxTickerType
import com.http.sample.app.{ ExchangeClient, ExchangeSubscription }

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
class GdaxClient extends ExchangeClient {

  override val baseUrl = "https://api.gdax.com/products"

  def subscribe(gdaxTickerType: GdaxTickerType): SubscriptionId =
    super.subscribe(new GdaxSubscription(gdaxTickerType))

}
