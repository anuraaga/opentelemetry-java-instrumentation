/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.test.annotation;

import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.trace.Tracer;
import java.util.concurrent.Callable;

public class SayTracedHello {

  private static final Tracer TRACER = OpenTelemetry.getTracer("io.opentelemetry.auto");

  @com.appoptics.api.ext.LogMethod
  public String appoptics() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan()
        .setAttribute("providerAttr", "AppOptics");
    return "hello!";
  }

  @com.newrelic.api.agent.Trace
  public String newrelic() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan()
        .setAttribute("providerAttr", "NewRelic");
    return "hello!";
  }

  @com.signalfx.tracing.api.Trace
  public String signalfx() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan()
        .setAttribute("providerAttr", "SignalFx");
    return "hello!";
  }

  @com.tracelytics.api.ext.LogMethod
  public String tracelytics() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan()
        .setAttribute("providerAttr", "Tracelytics");
    return "hello!";
  }

  @datadog.trace.api.Trace
  public String datadog() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan()
        .setAttribute("providerAttr", "Datadog");
    return "hello!";
  }

  @io.opentracing.contrib.dropwizard.Trace
  public String dropwizard() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan()
        .setAttribute("providerAttr", "Dropwizard");
    return "hello!";
  }

  @kamon.annotation.Trace("spanName")
  public String kamonold() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan()
        .setAttribute("providerAttr", "KamonOld");
    return "hello!";
  }

  @kamon.annotation.api.Trace
  public String kamonnew() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan()
        .setAttribute("providerAttr", "KamonNew");
    return "hello!";
  }

  @org.springframework.cloud.sleuth.annotation.NewSpan
  public String sleuth() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan()
        .setAttribute("providerAttr", "Sleuth");
    return "hello!";
  }

  @io.opentracing.contrib.dropwizard.Trace
  public static String sayHello() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan().setAttribute("myattr", "test");
    return "hello!";
  }

  @io.opentracing.contrib.dropwizard.Trace
  public static String sayHELLOsayHA() {
    io.opentelemetry.trace.TracingContextUtils.getCurrentSpan().setAttribute("myattr", "test2");
    return sayHello() + sayHello();
  }

  @io.opentracing.contrib.dropwizard.Trace
  public static String sayERROR() {
    throw new RuntimeException();
  }

  public static String fromCallable() {
    return new Callable<String>() {
      @com.newrelic.api.agent.Trace
      @Override
      public String call() {
        return "Howdy!";
      }
    }.call();
  }

  public static String fromCallableWhenDisabled() {
    return new Callable<String>() {
      @com.newrelic.api.agent.Trace
      @Override
      public String call() {
        return "Howdy!";
      }
    }.call();
  }
}
