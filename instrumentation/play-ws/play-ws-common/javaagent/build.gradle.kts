plugins {
  id("otel.javaagent-instrumentation")
}

def scalaVersion = '2.12'

dependencies {
  compileOnly group: 'com.typesafe.play', name: "play-ahc-ws-standalone_$scalaVersion", version: '1.0.2'
}
