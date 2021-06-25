plugins {
  id("otel.javaagent-instrumentation")
  id("otel.scala-conventions")
  id("org.unbroken-dome.test-sets")
}

muzzle {
  pass {
    group = 'org.scala-lang'
    module = "scala-library"
    versions = "[2.8.0,2.12.0)"
    assertInverse = true
  }
}

testSets {
  slickTest {
    filter {
      // this is needed because "test.dependsOn slickTest", and so without this,
      // running a single test in the default test set will fail
      setFailOnNoMatchingTests(false)
    }
  }
}

compileSlickTestGroovy {
  classpath += files(sourceSets.slickTest.scala.classesDirectory)
}

dependencies {
  library "org.scala-lang:scala-library:2.8.0"

  latestDepTestLibrary "org.scala-lang:scala-library:2.11.+"

  testInstrumentation project(':instrumentation:jdbc:javaagent')

  slickTestImplementation project(':testing-common')
  slickTestImplementation "org.scala-lang:scala-library"
  slickTestImplementation "com.typesafe.slick:slick_2.11:3.2.0"
  slickTestImplementation "com.h2database:h2:1.4.197"
}

// Run Slick library tests along with the rest of tests
test.dependsOn slickTest
