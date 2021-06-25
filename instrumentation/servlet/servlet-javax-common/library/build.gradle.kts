plugins {
  id("otel.library-instrumentation")
}

dependencies {
  implementation "org.slf4j:slf4j-api"

  api(project(':instrumentation:servlet:servlet-common:library'))

  compileOnly "javax.servlet:servlet-api:2.2"

  testImplementation "javax.servlet:servlet-api:2.2"
  testImplementation "org.mockito:mockito-core"
  testImplementation "org.assertj:assertj-core"
}
