// give the user a nice default project!
import AssemblyKeys._ 

name := "$name$" 

organization := "$organization$" 

version := "$version$" 

/*
scmInfo := Some(
  ScmInfo(
    url("https://github.com/DeaconDesperado/gn-core"),
    "scm:git:https://github.com/DeaconDesperado/gn-core.git",
    Some("scm:git:git@github.com:DeaconDesperado/gn-core.git")
  )
)
*/
/* scala versions and options */
scalaVersion := "$scalaVersion$" 

crossScalaVersions := Seq(
/*  "2.9.3-RC1",
  "2.9.2",
  "2.9.1", "2.9.1-1",
  "2.9.0", "2.9.0-1",
  "2.8.0", "2.8.1", "2.8.2" */
)

// These options will be used for *all* versions.
scalacOptions ++= Seq(
  "-deprecation"
  ,"-unchecked"
  ,"-encoding", "UTF-8"
  ,"-target:jvm-1.6"
  ,"-Xlint"
  // "-optimise"   // this option will slow your build
)

scalacOptions ++= Seq(
  "-Yclosure-elim",
  "-Yinline"
)

// These language flags will be used only for 2.10.x.
// Uncomment those you need, or if you hate SIP-18, all of them.
scalacOptions <++= scalaVersion map { sv =>
  if (sv startsWith "2.10") List(
    "-Xverify"
    ,"-Ywarn-all"
    ,"-feature"
    ,"-language:postfixOps"
    // "-language:reflectiveCalls",
    // "-language:implicitConversions"
    // "-language:higherKinds",
    // "-language:existentials",
    // "-language:experimental.macros",
    // "-language:experimental.dynamics"
  )
  else Nil
}

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

val akka = "2.3.0"
val spray = "1.3.0"

/* dependencies */
libraryDependencies ++= Seq (
  "com.github.nscala-time" %% "nscala-time" % "0.8.0"
  // -- network --
  ,"net.databinder.dispatch" %% "dispatch-core" % "0.11.1"
  ,"net.databinder.dispatch" %% "dispatch-jsoup" % "0.11.1"
  // -- testing --
  // -- Logging --
  ,"ch.qos.logback" % "logback-classic" % "1.1.1"
  // -- Akka --
  ,"com.typesafe.akka" %% "akka-testkit" % akka % "test"
  ,"com.typesafe.akka" %% "akka-actor" % akka
  ,"com.typesafe.akka" %% "akka-slf4j" % akka
  // -- Sql --
  //,"com.typesafe.slick" %% "slick" % "1.0.1"
  // -- Spray --
  ,"io.spray" % "spray-routing" % spray
  ,"io.spray" % "spray-client" % spray
  ,"io.spray" % "spray-testkit" % spray % "test"
  // -- json --
  ,"org.json4s" %% "json4s-jackson" % "3.2.7"
  ,"org.json4s" %% "json4s-ext" % "3.2.7"
  // -- config --
  ,"com.typesafe" % "config" % "1.2.0"
)

/* you may need these repos */
resolvers ++= Seq(
  // Resolver.sonatypeRepo("snapshots")
  // Resolver.typesafeRepo("releases")
  //"spray repo" at "http://repo.spray.io",
  //"brando" at "http://chrisdinn.github.io/releases/",
  "typesafe" at "http://repo.typesafe.com/typesafe/releases/"
)

//atmosSettings

seq(Revolver.settings: _*)

//Set development config for revolver
javaOptions in Revolver.reStart += "-Denv=dev"

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>  {
    case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
    case PathList("org", "apache", "commons", xs @ _*) => MergeStrategy.first
    case PathList("org", "jboss", "netty", xs @ _*) => MergeStrategy.first
    case PathList("org", "slf4j", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", "thrift", xs @ _*) => MergeStrategy.first 
    case PathList("org", "hamcrest", xs @ _*) => MergeStrategy.first 
    case PathList("com", "esotericsoftware", "minlog", xs @ _*) => MergeStrategy.first // kryo-2.21.jar vs minlog-1.2.jar
    case "about.html"                                  => MergeStrategy.rename
    case "overview.html"                                  => MergeStrategy.discard
    case "plugin.xml"                                  => MergeStrategy.discard
    case "jboss-beans.xml"                                  => MergeStrategy.discard
    case "pom.propertiese"                                  => MergeStrategy.discard
    case x => old(x)
  }
}
