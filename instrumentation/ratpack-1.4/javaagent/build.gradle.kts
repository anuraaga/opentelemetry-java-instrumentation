plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "io.ratpack"
    module = 'ratpack-core'
    versions = "[1.4.0,)"
  }
}

dependencies {
  library "io.ratpack:ratpack-core:1.4.0"

  implementation project(':instrumentation:netty:netty-4.1:javaagent')

  testLibrary "io.ratpack:ratpack-test:1.4.0"

  if (JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_11)) {
    testImplementation "com.sun.activation:jakarta.activation:1.2.2"
  }
}

// Requires old Guava. Can't use enforcedPlatform since predates BOM
configurations.testRuntimeClasspath.resolutionStrategy.force "com.google.guava:guava:19.0"
