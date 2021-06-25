plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "org.mongodb"
    module = "mongo-java-driver"
    versions = "[3.7, 4.0)"
    assertInverse = true
  }
  pass {
    group = "org.mongodb"
    module = "mongodb-driver-core"
    // this instrumentation is backwards compatible with early versions of the new API that shipped in 3.7
    // the legacy API instrumented in mongo-3.1 continues to be shipped in 4.x, but doesn't conflict here
    // because they are triggered by different types: MongoClientSettings(new) vs MongoClientOptions(legacy)
    versions = "[3.7, 4.0)"
    assertInverse = true
  }
}

dependencies {
  implementation(project(':instrumentation:mongo:mongo-3.1:library'))

  // a couple of test attribute verifications don't pass until 3.8.0
  library "org.mongodb:mongo-java-driver:3.8.0"

  testImplementation project(':instrumentation:mongo:mongo-testing')
}
