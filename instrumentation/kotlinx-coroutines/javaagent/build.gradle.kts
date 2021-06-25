plugins {
  id("org.jetbrains.kotlin.jvm")
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = 'org.jetbrains.kotlinx'
    module = 'kotlinx-coroutines-core'
    versions = "[1.0.0,1.3.8)"
  }
  // 1.3.9 (and beyond?) have changed how artifact names are resolved due to multiplatform variants
  pass {
    group = 'org.jetbrains.kotlinx'
    module = 'kotlinx-coroutines-core-jvm'
    versions = "[1.3.9,)"
  }
}
dependencies {
  compileOnly "io.opentelemetry:opentelemetry-extension-kotlin"
  compileOnly "org.jetbrains.kotlin:kotlin-stdlib-jdk8"

  testImplementation "io.opentelemetry:opentelemetry-extension-kotlin"
  testImplementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
  // Use first version with flow support since we have tests for it.
  testLibrary "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0"
}

tasks.named('compileTestGroovy').configure {
  //Note: look like it should be `classpath += files(sourceSets.test.kotlin.classesDirectory)`
  //instead, but kotlin plugin doesn't support it (yet?)
  classpath += files(compileTestKotlin.destinationDir)
}