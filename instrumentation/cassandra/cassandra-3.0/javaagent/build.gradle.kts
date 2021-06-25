plugins {
  id("otel.javaagent-instrumentation")
}

ext {
  cassandraDriverTestVersions = "[3.0,4.0)"
}

muzzle {

  pass {
    group = "com.datastax.cassandra"
    module = "cassandra-driver-core"
    versions = cassandraDriverTestVersions
    assertInverse = true
  }

  // Making sure that instrumentation works with recent versions of Guava which removed method
  // Futures::transform(input, function) in favor of Futures::transform(input, function, executor)
  pass {
    name = "Newest versions of Guava"
    group = "com.datastax.cassandra"
    module = "cassandra-driver-core"
    versions = cassandraDriverTestVersions
    // While com.datastax.cassandra uses old versions of Guava, users may depends themselves on newer versions of Guava
    extraDependency "com.google.guava:guava:27.0-jre"
  }
}

dependencies {
  library "com.datastax.cassandra:cassandra-driver-core:3.0.0"

  compileOnly "com.google.auto.value:auto-value-annotations"
  annotationProcessor "com.google.auto.value:auto-value"

  testLibrary "com.datastax.cassandra:cassandra-driver-core:3.2.0"
  testInstrumentation project(':instrumentation:guava-10.0:javaagent')

  latestDepTestLibrary "com.datastax.cassandra:cassandra-driver-core:3.+"
}

// Requires old Guava. Can't use enforcedPlatform since predates BOM
configurations.testRuntimeClasspath.resolutionStrategy.force "com.google.guava:guava:19.0"
