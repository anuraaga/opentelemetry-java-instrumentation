plugins {
  id("otel.javaagent-testing")
}

dependencies {
  testImplementation "org.apache.commons:commons-lang3:3.12.0"

  testInstrumentation project(":instrumentation:internal:internal-class-loader:javaagent")
}
