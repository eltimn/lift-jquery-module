name := "Lift-JQuery-Module"

organization := "net.liftmodules"

version in ThisBuild := "2.6"

liftVersion in ThisBuild <<= liftVersion ?? "2.6-SNAPSHOT"

liftEdition in ThisBuild <<= liftVersion apply { _.substring(0,3) }

name <<= (name, liftEdition) { (n, e) =>  n + "_" + e }

scalaVersion  in ThisBuild := "2.10.0"

scalacOptions ++= Seq("-unchecked", "-deprecation")

crossScalaVersions := Seq("2.10.0", "2.9.2", "2.9.1-1", "2.9.1")

logLevel := Level.Info  

resolvers ++= Seq(
  "CB Central Mirror" at "http://repo.cloudbees.com/content/groups/public",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
)

libraryDependencies <++= liftVersion { v =>
    "net.liftweb" %% "lift-webkit"  % v % "provided" ::
    "net.liftweb" %% "lift-testkit" % v % "provided" ::
    Nil
}

libraryDependencies <++= scalaVersion { sv =>
  "ch.qos.logback" % "logback-classic" % "1.0.0" % "provided" ::
  "log4j" % "log4j" % "1.2.16" % "provided" ::
  (sv match {
      case "2.10.0" | "2.9.3" | "2.9.2" | "2.9.1" | "2.9.1-1" => "org.specs2" %% "specs2" % "1.12.3" % "test"
      case _ => "org.specs2" %% "specs2" % "1.12.3" % "test"
      }) ::
   (sv match {
      case "2.10.0" | "2.9.3" | "2.9.2" => "org.scalacheck" %% "scalacheck" % "1.10.0" % "test"
      case _ => "org.scalacheck" %% "scalacheck" % "1.10.0" % "test"
      }) ::
  Nil
}

//################################################################
//#### THE YUI COMPRESSION BUILD
//## 
//##  
//## 
//################################################################
seq(yuiSettings: _*)

excludeFilter in (Compile, YuiCompressorKeys.jsResources) := "*-debug.js" | "*-min.js" 

excludeFilter in (Compile, YuiCompressorKeys.cssResources) := "*-debug.css" | "*-min.css"

YuiCompressorKeys.minSuffix := "-min"

//################################################################
//#### Publish to sonatype org
//## 
//##  
//## 
//################################################################
credentials += Credentials(Path.userHome / ".sbt" / "liftmodules" /".credentials" )

credentials += Credentials( file("/private/liftmodules/sonatype.credentials") )

//pgpPublicRing := file(Path.userHome / ".gnupg" / "mykey.asc")

publishTo <<= version { v: String =>
   val sonatype = "https://oss.sonatype.org/"
   if (v.trim.endsWith("SNAPSHOT"))
	 Some("snapshots" at sonatype + "content/repositories/snapshots")
   else
     Some("releases" at sonatype + "service/local/staging/deploy/maven2")
   }

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

pomExtra := (
  <url>https://github.com/karma4u101/lift-jquery-module</url>
  <licenses>
    <license>
      <name>The Apache Software License, Version 2.0</name>
      <url>http://maven.apache.org/ref/2.1.0/maven-profile/license.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:karma4u101/lift-jquery-module.git</url>
    <connection>scm:git:git@github.com:karma4u101/lift-jquery-module.git</connection>
  </scm>
  <developers>
    <developer>
      <id>karma4u101</id>
      <name>Peter Petersson</name>
      <url>http://www.media4u101.se</url>
    </developer>
  </developers>
)






