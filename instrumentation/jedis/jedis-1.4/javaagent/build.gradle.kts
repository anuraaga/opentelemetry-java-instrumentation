plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "redis.clients"
    module = "jedis"
    versions = "[1.4.0,3.0.0)"
    assertInverse = true
  }
}

dependencies {
  library "redis.clients:jedis:1.4.0"

  compileOnly "com.google.auto.value:auto-value-annotations"
  annotationProcessor "com.google.auto.value:auto-value"

  // Jedis 3.0 has API changes that prevent instrumentation from applying
  latestDepTestLibrary "redis.clients:jedis:2.+"
}
