plugins {
  id("otel.java-conventions")
}

def scalaVersion = '2.12'

dependencies {
  api project(':testing-common')
  api group: 'com.typesafe.play', name: "play-ahc-ws-standalone_$scalaVersion", version: '1.0.2'

  implementation "org.codehaus.groovy:groovy-all"
  implementation "io.opentelemetry:opentelemetry-api"
  implementation "org.spockframework:spock-core"
}