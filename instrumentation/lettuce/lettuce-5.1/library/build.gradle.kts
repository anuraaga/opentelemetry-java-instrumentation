plugins {
  id("otel.library-instrumentation")
  id("net.ltgt.nullaway")
}

dependencies {
  library "io.lettuce:lettuce-core:5.1.0.RELEASE"

  implementation project(':instrumentation:lettuce:lettuce-common:library')

  testImplementation project(':instrumentation:lettuce:lettuce-5.1:testing')
  testImplementation project(':instrumentation:reactor-3.1:library')
}

test {
  systemProperty "testLatestDeps", testLatestDeps
}
