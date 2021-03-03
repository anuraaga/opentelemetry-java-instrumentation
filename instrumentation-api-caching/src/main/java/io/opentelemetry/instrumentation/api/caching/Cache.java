/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.instrumentation.api.caching;

import java.util.function.Function;

public interface Cache<K, V> {

  static CacheBuilder newBuilder() {
    return new CacheBuilder();
  }

  V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction);
}
