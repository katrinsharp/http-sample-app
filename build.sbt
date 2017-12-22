// *****************************************************************************
// Projects
// *****************************************************************************

lazy val `http-sample-app` =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin, GitVersioning)
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
        library.scalaCheck % Test,
        library.scalaTest  % Test,
        library.okHttp
      )
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {
    object Version {
      val scalaCheck = "1.13.4"
      val scalaTest  = "3.0.1"
    }
    val scalaCheck = "org.scalacheck"       %% "scalacheck" % Version.scalaCheck
    val scalaTest  = "org.scalatest"        %% "scalatest"  % Version.scalaTest
    val okHttp     = "com.squareup.okhttp3" % "okhttp-ws" % "3.4.2"
  }

// *****************************************************************************
// Settings
// *****************************************************************************        |

lazy val settings =
  commonSettings ++
  gitSettings ++
  headerSettings

lazy val commonSettings =
  Seq(
    // scalaVersion and crossScalaVersions from .travis.yml via sbt-travisci
    scalaVersion := "2.11.8",
    // crossScalaVersions := Seq(scalaVersion.value, "2.11.8"),
    organization := "default",
    licenses += ("Apache 2.0",
                 url("http://www.apache.org/licenses/LICENSE-2.0")),
    mappings.in(Compile, packageBin) +=
      baseDirectory.in(ThisBuild).value / "LICENSE" -> "LICENSE",
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding", "UTF-8"
    ),
    javacOptions ++= Seq(
      "-source", "1.8",
      "-target", "1.8"
    ),
    unmanagedSourceDirectories.in(Compile) :=
      Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) :=
      Seq(scalaSource.in(Test).value),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.4.16",
      "com.typesafe.akka" %% "akka-http" % "10.0.1",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.1"
    )
)

lazy val gitSettings =
  Seq(
    git.useGitDescribe := true
  )

import de.heikoseeberger.sbtheader.HeaderPattern
import de.heikoseeberger.sbtheader.license._
lazy val headerSettings =
  Seq(
    headers := Map("scala" -> Apache2_0("2017", "Students"))
  )
