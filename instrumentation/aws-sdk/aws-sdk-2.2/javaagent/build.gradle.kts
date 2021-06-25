plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "software.amazon.awssdk"
    module = "aws-core"
    versions = "[2.2.0,)"
    // Used by all SDK services, the only case it isn't is an SDK extension such as a custom HTTP
    // client, which is not target of instrumentation anyways.
    extraDependency "software.amazon.awssdk:protocol-core"
  }
}

dependencies {
  implementation project(':instrumentation:aws-sdk:aws-sdk-2.2:library')

  library "software.amazon.awssdk:aws-core:2.2.0"

  testImplementation project(':instrumentation:aws-sdk:aws-sdk-2.2:testing')
  // Make sure these don't add HTTP headers
  testImplementation project(':instrumentation:apache-httpclient:apache-httpclient-4.0:javaagent')
  testImplementation project(':instrumentation:netty:netty-4.1:javaagent')
}

tasks.withType(Test).configureEach {
  // TODO run tests both with and without experimental span attributes
  jvmArgs "-Dotel.instrumentation.aws-sdk.experimental-span-attributes=true"
}
