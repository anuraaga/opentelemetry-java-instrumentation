plugins {
  id("otel.javaagent-instrumentation")
}

/*
Note: The Interceptor class for OkHttp was not introduced until 2.2+, so we need to make sure the
instrumentation is not loaded unless the dependency is 2.2+.
*/
muzzle {
  pass {
    group = "com.squareup.okhttp"
    module = "okhttp"
    versions = "[2.2,3)"
    assertInverse = true
  }
}

dependencies {
  library("com.squareup.okhttp:okhttp:2.2.0")

  latestDepTestLibrary "com.squareup.okhttp:okhttp:[2.6,3)"
}
