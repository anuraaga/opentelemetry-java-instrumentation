plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "biz.paluch.redis"
    module = "lettuce"
    versions = "[4.0.Final,)"
    assertInverse = true
  }
}


dependencies {
  library "biz.paluch.redis:lettuce:4.0.Final"

  latestDepTestLibrary "biz.paluch.redis:lettuce:4.+"
}

tasks.withType(Test).configureEach {
  // TODO run tests both with and without experimental span attributes
  jvmArgs "-Dotel.instrumentation.lettuce.experimental-span-attributes=true"
}
