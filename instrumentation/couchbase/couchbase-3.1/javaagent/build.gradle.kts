plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "com.couchbase.client"
    module = "java-client"
    versions = "[3.1,3.1.6)"
    // these versions were released as ".bundle" instead of ".jar"
    skip('2.7.5', '2.7.8')
    assertInverse = true
  }
}

dependencies {
  implementation("com.couchbase.client:tracing-opentelemetry:0.3.3") {
    exclude(group: "com.couchbase.client", module: "core-io")
  }

  library "com.couchbase.client:core-io:2.1.0"

  testLibrary "com.couchbase.client:java-client:3.1.0"

  testImplementation group: "org.testcontainers", name: "couchbase", version: versions["org.testcontainers"]

  latestDepTestLibrary "com.couchbase.client:java-client:3.1.5"
  latestDepTestLibrary "com.couchbase.client:core-io:2.1.5"
}