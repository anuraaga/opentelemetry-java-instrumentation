plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  // Cant assert fails because muzzle assumes all instrumentations will fail
  // Instrumentations in jaxrs-2.0-common will pass
  pass {
    group = "org.glassfish.jersey.core"
    module = "jersey-server"
    versions = "[2.0,3.0.0)"
    extraDependency "javax.servlet:javax.servlet-api:3.1.0"
  }
  pass {
    group = "org.glassfish.jersey.containers"
    module = "jersey-container-servlet"
    versions = "[2.0,3.0.0)"
    extraDependency "javax.servlet:javax.servlet-api:3.1.0"
  }
}

dependencies {
  compileOnly "javax.ws.rs:javax.ws.rs-api:2.0"
  compileOnly "javax.servlet:javax.servlet-api:3.1.0"
  library "org.glassfish.jersey.core:jersey-server:2.0"
  library "org.glassfish.jersey.containers:jersey-container-servlet:2.0"

  implementation project(':instrumentation:jaxrs:jaxrs-2.0:jaxrs-2.0-common:javaagent')

  testInstrumentation project(':instrumentation:servlet:servlet-3.0:javaagent')
  testInstrumentation project(':instrumentation:servlet:servlet-javax-common:javaagent')

  testImplementation project(':instrumentation:jaxrs:jaxrs-2.0:jaxrs-2.0-testing')

  // First version with DropwizardTestSupport:
  testLibrary "io.dropwizard:dropwizard-testing:0.8.0"
  testImplementation "javax.xml.bind:jaxb-api:2.2.3"
  testImplementation "com.fasterxml.jackson.module:jackson-module-afterburner"

  latestDepTestLibrary "org.glassfish.jersey.core:jersey-server:2.+"
  latestDepTestLibrary "org.glassfish.jersey.containers:jersey-container-servlet:2.+"
  // this is needed because dropwizard-testing version 0.8.0 (above) pulls it in transitively,
  // but the latest version of dropwizard-testing does not
  latestDepTestLibrary "org.eclipse.jetty:jetty-webapp:9.+"
}

test {
  systemProperty 'testLatestDeps', testLatestDeps
}

tasks.withType(Test).configureEach {
  // TODO run tests both with and without experimental span attributes
  jvmArgs "-Dotel.instrumentation.jaxrs.experimental-span-attributes=true"
}
