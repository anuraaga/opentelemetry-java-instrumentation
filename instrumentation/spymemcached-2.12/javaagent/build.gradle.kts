plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "net.spy"
    module = 'spymemcached'
    versions = "[2.12.0,)"
    assertInverse = true
  }
}

dependencies {
  library "net.spy:spymemcached:2.12.0"

  testImplementation "com.google.guava:guava"
}

tasks.withType(Test).configureEach {
  // TODO run tests both with and without experimental span attributes
  jvmArgs "-Dotel.instrumentation.spymemcached.experimental-span-attributes=true"
}
