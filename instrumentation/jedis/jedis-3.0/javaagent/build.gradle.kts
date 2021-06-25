plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  fail {
    group = "redis.clients"
    module = "jedis"
    versions = "[,3.0.0)"
  }

  pass {
    group = "redis.clients"
    module = "jedis"
    versions = "[3.0.0,)"
  }
}

dependencies {
  library "redis.clients:jedis:3.0.0"

  compileOnly "com.google.auto.value:auto-value-annotations"
  annotationProcessor "com.google.auto.value:auto-value"

  // ensures jedis-1.4 instrumentation does not load with jedis 3.0+ by failing
  // the tests in the event it does. The tests will end up with double spans
  testInstrumentation project(':instrumentation:jedis:jedis-1.4:javaagent')

  testLibrary "redis.clients:jedis:3.+"
}
