plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  // We have two independent covariants, so we have to test them independently.
  pass {
    group = 'org.springframework.data'
    module = 'spring-data-commons'
    versions = "[1.8.0.RELEASE,]"
    extraDependency "org.springframework:spring-aop:1.2"
    assertInverse = true
  }
  pass {
    group = 'org.springframework'
    module = 'spring-aop'
    versions = "[1.2,]"
    extraDependency "org.springframework.data:spring-data-commons:1.8.0.RELEASE"
    assertInverse = true
  }
}

// DQH - API changes that impact instrumentation occurred in spring-data-commons in March 2014.
// For now, that limits support to spring-data-commons 1.9.0 (maybe 1.8.0).
// For testing, chose a couple spring-data modules that are old enough to work with 1.9.0.
dependencies {
  library "org.springframework.data:spring-data-commons:1.8.0.RELEASE"
  compileOnly("org.springframework:spring-aop:1.2")

  testImplementation "org.spockframework:spock-spring:${versions["org.spockframework"]}"
  testLibrary "org.springframework:spring-test:3.0.0.RELEASE"
  testLibrary "org.springframework.data:spring-data-jpa:1.8.0.RELEASE"

  // JPA dependencies
  testInstrumentation project(':instrumentation:jdbc:javaagent')
  testImplementation "com.mysema.querydsl:querydsl-jpa:3.7.4"
  testImplementation "org.hsqldb:hsqldb:2.0.0"
  testLibrary "org.hibernate:hibernate-entitymanager:4.3.0.Final"

  latestDepTestLibrary "org.hibernate:hibernate-entitymanager:5.+"
}

