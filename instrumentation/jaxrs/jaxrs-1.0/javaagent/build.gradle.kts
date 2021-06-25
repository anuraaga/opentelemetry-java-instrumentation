plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "javax.ws.rs"
    module = "jsr311-api"
    versions = "[0.5,)"
  }
  fail {
    group = "javax.ws.rs"
    module = "javax.ws.rs-api"
    versions = "[,]"
  }
}

dependencies {
  compileOnly "javax.ws.rs:jsr311-api:1.1.1"

  testImplementation "io.dropwizard:dropwizard-testing:0.7.1"
  testImplementation "javax.xml.bind:jaxb-api:2.2.3"
}
