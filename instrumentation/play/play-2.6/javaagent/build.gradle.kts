plugins {
  id("otel.javaagent-instrumentation")
}

def scalaVersion = '2.11'
def playVersion = '2.6.0'

muzzle {
  pass {
    group = 'com.typesafe.play'
    module = "play_$scalaVersion"
    versions = "[$playVersion,)"
    assertInverse = true
    // versions 2.3.9 and 2.3.10 depends on com.typesafe.netty:netty-http-pipelining:1.1.2
    // which does not exist
    skip('2.3.9', '2.3.10')
  }
  pass {
    group = 'com.typesafe.play'
    module = 'play_2.12'
    versions = "[$playVersion,)"
    assertInverse = true
  }
  pass {
    group = 'com.typesafe.play'
    module = 'play_2.13'
    versions = "[$playVersion,)"
    assertInverse = true
  }
}

otelJava {
  // Play doesn't work with Java 9+ until 2.6.12
  maxJavaVersionForTests = JavaVersion.VERSION_1_8
}

dependencies {
  // TODO(anuraaga): Something about library configuration doesn't work well with scala compilation
  // here.
  compileOnly group: 'com.typesafe.play', name: "play_$scalaVersion", version: playVersion

  testInstrumentation project(':instrumentation:netty:netty-4.0:javaagent')
  testInstrumentation project(':instrumentation:netty:netty-4.1:javaagent')
  testInstrumentation project(':instrumentation:akka-actor-2.5:javaagent')
  testInstrumentation project(':instrumentation:akka-http-10.0:javaagent')

  testLibrary group: 'com.typesafe.play', name: "play-java_$scalaVersion", version: playVersion
  // TODO: Play WS is a separately versioned library starting with 2.6 and needs separate instrumentation.
  testLibrary(group: 'com.typesafe.play', name: "play-test_$scalaVersion", version: playVersion) {
    exclude group: 'org.eclipse.jetty.websocket', module: 'websocket-client'
  }

  // TODO: This should be changed to the latest in scala 2.13 instead of 2.11 since its ahead
  latestDepTestLibrary group: 'com.typesafe.play', name: "play-java_$scalaVersion", version: '2.+'
  latestDepTestLibrary(group: 'com.typesafe.play', name: "play-test_$scalaVersion", version: '2.+') {
    exclude group: 'org.eclipse.jetty.websocket', module: 'websocket-client'
  }
}
