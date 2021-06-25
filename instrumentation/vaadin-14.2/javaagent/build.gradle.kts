plugins {
  id("otel.javaagent-instrumentation")
  id("org.unbroken-dome.test-sets")
}

muzzle {
  fail {
    group = "com.vaadin"
    module = "flow-server"
    versions = "[,2.2.0)"
  }
  pass {
    group = "com.vaadin"
    module = "flow-server"
    versions = "[2.2.0,3)"
  }
  fail {
    group = "com.vaadin"
    module = "flow-server"
    versions = "[3.0.0,3.1.0)"
  }
  pass {
    group = "com.vaadin"
    module = "flow-server"
    versions = "[3.1.0,)"
  }
}


testSets {
  vaadin142Test
  vaadin14LatestTest
  vaadin16Test
  latestDepTest {
    dirName = 'vaadinLatestTest'
  }
}

test.dependsOn vaadin142Test, vaadin16Test
if (findProperty('testLatestDeps')) {
  test.dependsOn vaadin14LatestTest
}

dependencies {
  compileOnly "com.vaadin:flow-server:2.2.0"

  vaadin16TestImplementation 'com.vaadin:vaadin-spring-boot-starter:16.0.0'
  vaadin142TestImplementation 'com.vaadin:vaadin-spring-boot-starter:14.2.0'

  testImplementation project(':instrumentation:vaadin-14.2:testing')
  testImplementation(project(':testing-common')) {
    exclude(module: 'jetty-server')
  }

  testInstrumentation project(':instrumentation:servlet:servlet-3.0:javaagent')
  testInstrumentation project(':instrumentation:servlet:servlet-javax-common:javaagent')
  testInstrumentation project(':instrumentation:tomcat:tomcat-7.0:javaagent')

  vaadin14LatestTestImplementation 'com.vaadin:vaadin-spring-boot-starter:14.+'
  latestDepTestImplementation 'com.vaadin:vaadin-spring-boot-starter:+'
}
