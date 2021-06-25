plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "org.apache.httpcomponents.client5"
    module = "httpclient5"
    versions = "[5.0,)"
  }
}

dependencies {
  library "org.apache.httpcomponents.client5:httpclient5:5.0"
}
