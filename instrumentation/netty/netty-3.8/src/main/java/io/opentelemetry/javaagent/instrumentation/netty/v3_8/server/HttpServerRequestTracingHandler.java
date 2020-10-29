/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.netty.v3_8.server;

import static io.opentelemetry.javaagent.instrumentation.netty.v3_8.server.NettyHttpServerTracer.TRACER;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.javaagent.instrumentation.api.ContextStore;
import io.opentelemetry.javaagent.instrumentation.netty.v3_8.ChannelTraceContext;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpRequest;

public class HttpServerRequestTracingHandler extends SimpleChannelUpstreamHandler {

  private final ContextStore<Channel, ChannelTraceContext> contextStore;

  public HttpServerRequestTracingHandler(ContextStore<Channel, ChannelTraceContext> contextStore) {
    this.contextStore = contextStore;
  }

  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent msg) {
    ChannelTraceContext channelTraceContext =
        contextStore.putIfAbsent(ctx.getChannel(), ChannelTraceContext.Factory.INSTANCE);

    if (!(msg.getMessage() instanceof HttpRequest)) {
      Context serverContext = TRACER.getServerContext(channelTraceContext);
      if (serverContext == null) {
        ctx.sendUpstream(msg);
      } else {
        try (Scope ignored = serverContext.makeCurrent()) {
          ctx.sendUpstream(msg);
        }
      }
      return;
    }

    HttpRequest request = (HttpRequest) msg.getMessage();

    Span span = TRACER.startSpan(request, ctx.getChannel(), "netty.request");
    try (Scope ignored = TRACER.startScope(span, channelTraceContext)) {
      ctx.sendUpstream(msg);
    } catch (Throwable throwable) {
      TRACER.endExceptionally(span, throwable);
      throw throwable;
    }
  }
}
