plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "jakarta.servlet"
    module = 'jakarta.servlet-api'
    versions = "[5.0.0,)"
    assertInverse = true
  }
}

dependencies {
  api(project(':instrumentation:servlet:servlet-5.0:library'))
  implementation(project(':instrumentation:servlet:servlet-common:javaagent'))
  compileOnly "jakarta.servlet:jakarta.servlet-api:5.0.0"

  testLibrary "org.eclipse.jetty:jetty-server:11.0.0"
  testLibrary "org.eclipse.jetty:jetty-servlet:11.0.0"
  testLibrary "org.apache.tomcat.embed:tomcat-embed-core:10.0.0"
  testLibrary "org.apache.tomcat.embed:tomcat-embed-jasper:10.0.0"
}
