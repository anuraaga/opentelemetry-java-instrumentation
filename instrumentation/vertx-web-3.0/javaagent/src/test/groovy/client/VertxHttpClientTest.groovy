/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package client

import io.opentelemetry.instrumentation.test.AgentTestTrait
import io.opentelemetry.instrumentation.test.base.HttpClientTest
import io.opentelemetry.instrumentation.test.base.SingleConnection
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.http.HttpClientOptions
import io.vertx.core.http.HttpClientResponse
import io.vertx.core.http.HttpMethod
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer
import spock.lang.Shared
import spock.lang.Timeout

@Timeout(10)
class VertxHttpClientTest extends HttpClientTest implements AgentTestTrait {

  @Shared
  def vertx = Vertx.vertx(new VertxOptions())
  @Shared
  def clientOptions = new HttpClientOptions().setConnectTimeout(CONNECT_TIMEOUT_MS)
  @Shared
  def httpClient = vertx.createHttpClient(clientOptions)

  @Override
  int doRequest(String method, URI uri, Map<String, String> headers, Consumer<Integer> callback) {
    CompletableFuture<HttpClientResponse> future = new CompletableFuture<>()
    def request = httpClient.request(HttpMethod.valueOf(method), uri.port, uri.host, "$uri")
    headers.each { request.putHeader(it.key, it.value) }
    request.handler { response ->
      callback?.accept(response.statusCode())
      future.complete(response)
    }
    request.end()

    return future.get().statusCode()
  }

  @Override
  boolean testRedirects() {
    false
  }

  @Override
  boolean testConnectionFailure() {
    false
  }

  boolean testRemoteConnection() {
    // FIXME: figure out how to configure timeouts.
    false
  }

  @Override
  SingleConnection createSingleConnection(String host, int port) {
    //This test fails on Vert.x 3.0 and only works starting from 3.1
    //Most probably due to https://github.com/eclipse-vertx/vert.x/pull/1126
    boolean shouldRun = Boolean.getBoolean("testLatestDeps")
    return shouldRun ? new VertxSingleConnection(host, port) : null
  }
}
