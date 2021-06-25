plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "javax.xml.ws"
    module = "jaxws-api"
    versions = "[2.0,]"
  }
}

dependencies {
  library "javax.xml.ws:jaxws-api:2.0"
  implementation project(":instrumentation:jaxws:jaxws-common:library")
}