/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.servlet.v5_0;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.instrumentation.servlet.common.async.AsyncContextInstrumentation;
import io.opentelemetry.javaagent.instrumentation.servlet.common.dispatcher.RequestDispatcherInstrumentation;
import io.opentelemetry.javaagent.instrumentation.servlet.common.response.HttpServletResponseInstrumentation;
import io.opentelemetry.javaagent.instrumentation.servlet.common.service.ServletAndFilterInstrumentation;
import io.opentelemetry.javaagent.tooling.InstrumentationModule;
import io.opentelemetry.javaagent.tooling.TypeInstrumentation;
import java.util.Arrays;
import java.util.List;

@AutoService(InstrumentationModule.class)
public class JakartaServletInstrumentationModule extends InstrumentationModule {
  private static final String BASE_PACKAGE = "jakarta.servlet";

  public JakartaServletInstrumentationModule() {
    super("servlet", "servlet-5.0");
  }

  @Override
  public List<TypeInstrumentation> typeInstrumentations() {
    return Arrays.asList(
        new AsyncContextInstrumentation(
            BASE_PACKAGE, adviceClassName(".async.AsyncDispatchAdvice")),
        new ServletAndFilterInstrumentation(
            BASE_PACKAGE, adviceClassName(".service.JakartaServletServiceAdvice")),
        new HttpServletResponseInstrumentation(
            BASE_PACKAGE, adviceClassName(".response.ResponseSendAdvice")),
        new RequestDispatcherInstrumentation(
            BASE_PACKAGE, adviceClassName(".dispatcher.RequestDispatcherAdvice")));
  }

  private static String adviceClassName(String suffix) {
    return JakartaServletInstrumentationModule.class.getPackage().getName() + suffix;
  }
}
