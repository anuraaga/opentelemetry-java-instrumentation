plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "com.google.guava"
    module = "guava"
    versions = "[10.0,]"
    assertInverse = true
  }
}

tasks.withType(Test).configureEach {
  // TODO run tests both with and without experimental span attributes
  jvmArgs "-Dotel.instrumentation.guava.experimental-span-attributes=true"
}

dependencies {
  library "com.google.guava:guava:10.0"

  implementation project(':instrumentation:guava-10.0:library')

  testImplementation "io.opentelemetry:opentelemetry-extension-annotations"
}
