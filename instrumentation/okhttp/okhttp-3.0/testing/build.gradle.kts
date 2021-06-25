plugins {
  id("otel.java-conventions")
}

dependencies {
  api(project(':testing-common')) {
    exclude module: 'okhttp'
  }

  api "com.squareup.okhttp3:okhttp:3.0.0"

  implementation "com.google.guava:guava"

  implementation "org.codehaus.groovy:groovy-all"
  implementation "io.opentelemetry:opentelemetry-api"
  implementation "org.spockframework:spock-core"
}
