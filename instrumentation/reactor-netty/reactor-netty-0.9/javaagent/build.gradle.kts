plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "io.projectreactor.netty"
    module = "reactor-netty"
    versions = "[0.9.0.RELEASE,1.0.0)"
  }
  fail {
    group = "io.projectreactor.netty"
    module = "reactor-netty-http"
    versions = "[1.0.0,)"
  }
}

dependencies {
  implementation project(':instrumentation:netty:netty-4.1:library')
  library "io.projectreactor.netty:reactor-netty:0.9.0.RELEASE"

  testInstrumentation project(':instrumentation:reactor-netty:reactor-netty-1.0:javaagent')
  testInstrumentation project(':instrumentation:netty:netty-4.1:javaagent')
  testInstrumentation project(':instrumentation:reactor-3.1:javaagent')

  latestDepTestLibrary "io.projectreactor.netty:reactor-netty:(,1.0.0)"
}
