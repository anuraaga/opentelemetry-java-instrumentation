plugins {
  id("otel.javaagent-instrumentation")
  id("otel.scala-conventions")
}

muzzle {
  pass {
    group = 'com.typesafe.akka'
    module = 'akka-actor_2.11'
    versions = "[2.5.0,)"
  }
  pass {
    group = 'com.typesafe.akka'
    module = 'akka-actor_2.12'
    versions = "[2.5.0,)"
  }
  pass {
    group = 'com.typesafe.akka'
    module = 'akka-actor_2.13'
    versions = "(,)"
  }
}

dependencies {
  compileOnly "com.typesafe.akka:akka-actor_2.11:2.5.0"
  testImplementation "com.typesafe.akka:akka-actor_2.11:2.5.0"

  latestDepTestLibrary "com.typesafe.akka:akka-actor_2.13:+"
}

if (findProperty('testLatestDeps')) {
  configurations {
    // akka artifact name is different for regular and latest tests
    testImplementation.exclude group: 'com.typesafe.akka', module: 'akka-actor_2.11'
  }
}