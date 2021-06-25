plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  fail {
    group = "io.projectreactor.netty"
    module = "reactor-netty"
    versions = "[,1.0.0)"
  }
  pass {
    group = "io.projectreactor.netty"
    module = "reactor-netty-http"
    versions = "[1.0.0,)"
    assertInverse = true
  }
}

dependencies {
  implementation project(':instrumentation:netty:netty-4.1:library')
  library "io.projectreactor.netty:reactor-netty-http:1.0.0"

  testInstrumentation project(':instrumentation:reactor-netty:reactor-netty-0.9:javaagent')
  testInstrumentation project(':instrumentation:netty:netty-4.1:javaagent')
  testInstrumentation project(':instrumentation:reactor-3.1:javaagent')
}
