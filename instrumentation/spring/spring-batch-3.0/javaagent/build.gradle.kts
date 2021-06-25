plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "org.springframework.batch"
    module = "spring-batch-core"
    versions = "[3.0.0.RELEASE,)"
    assertInverse = true
  }
}

dependencies {
  library "org.springframework.batch:spring-batch-core:3.0.0.RELEASE"

  testImplementation "javax.inject:javax.inject:1"
  // SimpleAsyncTaskExecutor context propagation
  testInstrumentation project(':instrumentation:spring:spring-core-2.0:javaagent')
}

tasks.withType(Test).configureEach {
  jvmArgs '-Dotel.instrumentation.spring-batch.enabled=true'
}
test {
  filter {
    excludeTestsMatching '*ChunkRootSpanTest'
    excludeTestsMatching '*ItemLevelSpanTest'
  }
}
test.finalizedBy(tasks.register("testChunkRootSpan", Test) {
  filter {
    includeTestsMatching '*ChunkRootSpanTest'
  }
  jvmArgs '-Dotel.instrumentation.spring-batch.experimental.chunk.new-trace=true'
}).finalizedBy(tasks.register("testItemLevelSpan", Test) {
  filter {
    includeTestsMatching '*ItemLevelSpanTest'
  }
  jvmArgs '-Dotel.instrumentation.spring-batch.item.enabled=true'
})
