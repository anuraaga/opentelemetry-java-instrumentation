plugins {
  id("otel.javaagent-instrumentation")
  id("org.unbroken-dome.test-sets")
}

muzzle {
  pass {
    group = "org.apache.logging.log4j"
    module = "log4j-core"
    versions = "[2.13.2,)"
    assertInverse = true
  }
}

testSets {
  // Very different codepaths when threadlocals are enabled or not so we check both.
  // Regression test for https://github.com/open-telemetry/opentelemetry-java-instrumentation/issues/2403
  testDisableThreadLocals {
    dirName = "test"
  }
}

dependencies {
  library "org.apache.logging.log4j:log4j-core:2.13.2"

  implementation project(':instrumentation:log4j:log4j-2.13.2:library')

  testImplementation project(':instrumentation:log4j:log4j-2-testing')
}

// Threadlocals are always false if is.webapp is true, so we make sure to override it because as of
// now testing-common includes jetty / servlet.
test {
  jvmArgs "-Dlog4j2.is.webapp=false"
  jvmArgs "-Dlog4j2.enable.threadlocals=true"
}

testDisableThreadLocals {
  jvmArgs "-Dlog4j2.is.webapp=false"
  jvmArgs "-Dlog4j2.enable.threadlocals=false"
}

check.dependsOn testDisableThreadLocals