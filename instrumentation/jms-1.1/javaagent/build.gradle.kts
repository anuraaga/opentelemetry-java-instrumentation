plugins {
  id("otel.javaagent-instrumentation")
  id("org.unbroken-dome.test-sets")
}

muzzle {
  pass {
    group = "javax.jms"
    module = "jms-api"
    versions = "(,)"
  }
  pass {
    group = "javax.jms"
    module = "javax.jms-api"
    versions = "(,)"
  }
}

testSets {
  jms2Test {
    filter {
      // this is needed because "test.dependsOn jms2Test", and so without this,
      // running a single test in the default test set will fail
      setFailOnNoMatchingTests(false)
    }
  }
}

test.dependsOn jms2Test
dependencies {
  compileOnly "javax.jms:jms-api:1.1-rev-1"

  testImplementation "javax.annotation:javax.annotation-api:1.3.2"
  testImplementation("org.springframework.boot:spring-boot-starter-activemq:${versions["org.springframework.boot"]}")
  testImplementation("org.springframework.boot:spring-boot-starter-test:${versions["org.springframework.boot"]}") {
    exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
  }

  jms2TestImplementation "org.hornetq:hornetq-jms-client:2.4.7.Final"
  jms2TestImplementation("org.hornetq:hornetq-jms-server:2.4.7.Final") {
    // this doesn't exist in maven central, and doesn't seem to be needed anyways
    exclude group: 'org.jboss.naming', module: 'jnpserver'
  }
}
