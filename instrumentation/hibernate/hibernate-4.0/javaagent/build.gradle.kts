plugins {
  id("otel.javaagent-instrumentation")
  id("org.unbroken-dome.test-sets")
}

muzzle {
  pass {
    group = "org.hibernate"
    module = "hibernate-core"
    versions = "[4.0.0.Final,)"
    assertInverse = true
  }
}

testSets {
  version5Test {
    dirName = 'test'
  }
  version6Test {
    dirName = 'hibernate6Test'
  }

  latestDepTest {
    dirName = 'test'
  }
}

test.dependsOn version5Test, version6Test

dependencies {
  compileOnly "org.hibernate:hibernate-core:4.0.0.Final"

  implementation project(':instrumentation:hibernate:hibernate-common:javaagent')

  testInstrumentation project(':instrumentation:jdbc:javaagent')
  // Added to ensure cross compatibility:
  testInstrumentation project(':instrumentation:hibernate:hibernate-3.3:javaagent')
  testInstrumentation project(':instrumentation:hibernate:hibernate-procedure-call-4.3:javaagent')

  testImplementation "com.h2database:h2:1.4.197"
  testImplementation "javax.xml.bind:jaxb-api:2.2.11"
  testImplementation "com.sun.xml.bind:jaxb-core:2.2.11"
  testImplementation "com.sun.xml.bind:jaxb-impl:2.2.11"
  testImplementation "javax.activation:activation:1.1.1"
  testImplementation "org.hsqldb:hsqldb:2.0.0"
  //First version to work with Java 14
  testImplementation "org.springframework.data:spring-data-jpa:1.8.0.RELEASE"

  testImplementation "org.hibernate:hibernate-core:4.0.0.Final"
  testImplementation "org.hibernate:hibernate-entitymanager:4.0.0.Final"

  version5TestImplementation "org.hibernate:hibernate-core:5.0.0.Final"
  version5TestImplementation "org.hibernate:hibernate-entitymanager:5.0.0.Final"
  version5TestImplementation "org.springframework.data:spring-data-jpa:2.3.0.RELEASE"

  version6TestImplementation "org.hibernate:hibernate-core:6.0.0.Alpha6"
  version6TestImplementation "org.hibernate:hibernate-entitymanager:6.0.0.Alpha6"
  version6TestImplementation "org.springframework.data:spring-data-jpa:2.3.0.RELEASE"

  // hibernate 6 is alpha so use 5 as latest version
  latestDepTestImplementation "org.hibernate:hibernate-core:5.+"
  latestDepTestImplementation "org.hibernate:hibernate-entitymanager:5.+"
  latestDepTestImplementation "org.springframework.data:spring-data-jpa:(2.4.0,)"
}
