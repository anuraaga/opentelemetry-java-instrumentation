plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group = "com.github.etaty"
    module = "rediscala_2.11"
    versions = "[1.5.0,)"
    assertInverse = true
  }

  pass {
    group = "com.github.etaty"
    module = "rediscala_2.12"
    versions = "[1.8.0,)"
    assertInverse = true
  }

  pass {
    group = "com.github.etaty"
    module = "rediscala_2.13"
    versions = "[1.9.0,)"
    assertInverse = true
  }

  pass {
    group = "com.github.Ma27"
    module = "rediscala_2.11"
    versions = "[1.8.1,)"
    assertInverse = true
  }

  pass {
    group = "com.github.Ma27"
    module = "rediscala_2.12"
    versions = "[1.8.1,)"
    assertInverse = true
  }

  pass {
    group = "com.github.Ma27"
    module = "rediscala_2.13"
    versions = "[1.9.0,)"
    assertInverse = true
  }
}

dependencies {
  library "com.github.etaty:rediscala_2.11:1.8.0"
}
