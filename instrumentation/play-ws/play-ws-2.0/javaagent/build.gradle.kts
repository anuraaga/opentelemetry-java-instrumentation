plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {

  pass {
    module = 'play-ahc-ws-standalone_2.11'
    group = 'com.typesafe.play'
    versions = '[2.0.0,]'
    assertInverse = true
  }

  pass {
    group = 'com.typesafe.play'
    module = 'play-ahc-ws-standalone_2.12'
    versions = '[2.0.0,2.1.0)'
    // 2.0.5 is missing play.shaded.ahc.org.asynchttpclient.AsyncHandler#onTlsHandshakeSuccess()V
    skip('2.0.5')
    assertInverse = true
  }

  // No Scala 2.13 versions below 2.0.6 exist
  pass {
    group = 'com.typesafe.play'
    module = 'play-ahc-ws-standalone_2.13'
    versions = '[2.0.6,2.1.0)'
  }
}

def scalaVersion = '2.12'

dependencies {
  library group: 'com.typesafe.play', name: "play-ahc-ws-standalone_$scalaVersion", version: '2.0.0'

  implementation project(':instrumentation:play-ws:play-ws-common:javaagent')

  testImplementation project(':instrumentation:play-ws:play-ws-testing')

  // These are to ensure cross compatibility
  testInstrumentation project(':instrumentation:netty:netty-4.0:javaagent')
  testInstrumentation project(':instrumentation:netty:netty-4.1:javaagent')
  testInstrumentation project(':instrumentation:akka-http-10.0:javaagent')
  testInstrumentation project(':instrumentation:akka-actor-2.5:javaagent')

  latestDepTestLibrary group: 'com.typesafe.play', name: "play-ahc-ws-standalone_$scalaVersion", version: '2.0.+'
}
