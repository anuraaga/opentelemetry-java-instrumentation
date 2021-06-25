plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = 'org.apache.wicket'
    module = 'wicket'
    versions = "[8.0.0,]"
    assertInverse = true
  }
}

dependencies {
  library "org.apache.wicket:wicket:8.0.0"

  testImplementation(project(':testing-common'))
  testImplementation "org.jsoup:jsoup:1.13.1"
  testImplementation "org.eclipse.jetty:jetty-server:8.0.0.v20110901"
  testImplementation "org.eclipse.jetty:jetty-servlet:8.0.0.v20110901"

  testInstrumentation project(":instrumentation:servlet:servlet-3.0:javaagent")
  testInstrumentation project(":instrumentation:servlet:servlet-javax-common:javaagent")
}
