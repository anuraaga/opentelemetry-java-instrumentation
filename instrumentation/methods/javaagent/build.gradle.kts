plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    coreJdk = true
  }
}

dependencies {
  compileOnly project(':javaagent-tooling')
}

tasks.withType(Test).configureEach {
  jvmArgs "-Dotel.instrumentation.methods.include=package.ClassName[method1,method2];MethodTest\$ConfigTracedCallable[call]"
}
