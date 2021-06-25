plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "javax.servlet"
    module = "servlet-api"
    versions = "[2.2, 3.0)"
    assertInverse = true
  }

  fail {
    group = "javax.servlet"
    module = 'javax.servlet-api'
    versions = "[3.0,)"
  }
}

dependencies {
  compileOnly "javax.servlet:servlet-api:2.2"
  api(project(':instrumentation:servlet:servlet-2.2:library'))
  implementation(project(':instrumentation:servlet:servlet-common:javaagent'))

  testInstrumentation project(':instrumentation:servlet:servlet-javax-common:javaagent')

  testImplementation(project(':testing-common')) {
    exclude group: 'org.eclipse.jetty', module: 'jetty-server'
  }
  testLibrary "org.eclipse.jetty:jetty-server:7.0.0.v20091005"
  testLibrary "org.eclipse.jetty:jetty-servlet:7.0.0.v20091005"

  latestDepTestLibrary "org.eclipse.jetty:jetty-server:7.+"
  latestDepTestLibrary "org.eclipse.jetty:jetty-servlet:7.+"
}
