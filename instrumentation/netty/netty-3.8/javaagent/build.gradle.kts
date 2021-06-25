plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "io.netty"
    module = "netty"
    versions = "[3.8.0.Final,4)"
    assertInverse = true
  }
  fail {
    group = "io.netty"
    module = "netty-all"
    versions = "[,]"
    excludeDependency 'io.netty:netty-tcnative'
  }
}

dependencies {
  compileOnly "io.netty:netty:3.8.0.Final"

  testLibrary "io.netty:netty:3.8.0.Final"
  testLibrary "com.ning:async-http-client:1.8.0"

  latestDepTestLibrary "io.netty:netty:3.10.+"
  latestDepTestLibrary "com.ning:async-http-client:1.9.+"
}

// We need to force the dependency to the earliest supported version because other libraries declare newer versions.
if (!testLatestDeps) {
  configurations.each {
    it.resolutionStrategy {
      eachDependency { DependencyResolveDetails details ->
        //specifying a fixed version for all libraries with io.netty' group
        if (details.requested.group == 'io.netty') {
          details.useVersion "3.8.0.Final"
        }
      }
    }
  }
}