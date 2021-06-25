plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "io.reactivex.rxjava2"
    module = "rxjava"
    versions = "[2.0.6,)"
    assertInverse = true
  }
}

tasks.withType(Test).configureEach {
  // TODO run tests both with and without experimental span attributes
  jvmArgs "-Dotel.instrumentation.rxjava.experimental-span-attributes=true"
}

dependencies {
  library "io.reactivex.rxjava2:rxjava:2.0.6"

  implementation project(":instrumentation:rxjava:rxjava-2.0:library")

  testImplementation "io.opentelemetry:opentelemetry-extension-annotations"
  testImplementation project(':instrumentation:rxjava:rxjava-2.0:testing')
}
