plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = 'io.vertx'
    module = 'vertx-web'
    versions = "[3.0.0,4.0.0)"
    //TODO we should split this module into client and server
    //They have different version applicability
//    assertInverse = true
  }
}

ext.vertxVersion = '3.0.0'

dependencies {
  library "io.vertx:vertx-web:${vertxVersion}"

  //We need both version as different versions of Vert.x use different versions of Netty
  testInstrumentation project(':instrumentation:netty:netty-4.0:javaagent')
  testInstrumentation project(':instrumentation:netty:netty-4.1:javaagent')
  testInstrumentation project(':instrumentation:jdbc:javaagent')

  testImplementation "io.vertx:vertx-jdbc-client:${vertxVersion}"

  // Vert.x 4.0 is incompatible with our tests.
  // 3.9.7 Requires Netty 4.1.60, no other version works with it.
  latestDepTestLibrary enforcedPlatform("io.netty:netty-bom:4.1.60.Final")
  latestDepTestLibrary "io.vertx:vertx-web:3.+"
  latestDepTestLibrary "io.vertx:vertx-web-client:3.+"
}

test {
  systemProperty "testLatestDeps", testLatestDeps
}
