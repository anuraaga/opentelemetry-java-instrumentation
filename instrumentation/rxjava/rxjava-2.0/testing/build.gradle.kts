plugins {
  id("otel.java-conventions")
}

dependencies {
  api project(':testing-common')

  api "io.reactivex.rxjava2:rxjava:2.1.3"

  implementation "com.google.guava:guava"

  implementation "org.codehaus.groovy:groovy-all"
  implementation "io.opentelemetry:opentelemetry-api"
  implementation "org.spockframework:spock-core"
}
