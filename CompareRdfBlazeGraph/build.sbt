name := "CompareRdfBlazeGraph"

version := "1.0"

scalaVersion := "2.10.5"



libraryDependencies += "org.apache.jena" % "jena-elephas-io" % "3.0.0"

libraryDependencies += "junit" % "junit" % "4.10"

val sparkVersion = "1.6.0-cdh5.7.0"

val hadoopVersion = "2.6.0-cdh5.7.0"

val scalaTestVersion = "2.2.6"

resolvers ++= Seq(
  "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
)

val isALibrary = false //this is a library project

val sparkExcludes =
  (moduleId: ModuleID) => moduleId.
    exclude("org.apache.hadoop", "hadoop-client").
    exclude("org.apache.hadoop", "hadoop-yarn-client").
    exclude("org.apache.hadoop", "hadoop-yarn-api").
    exclude("org.apache.hadoop", "hadoop-yarn-common").
    exclude("org.apache.hadoop", "hadoop-yarn-server-common").
    exclude("org.apache.hadoop", "hadoop-yarn-server-web-proxy")

val assemblyDependencies = (scope: String) => Seq(
  sparkExcludes("org.apache.spark" %% "spark-streaming-kafka" % sparkVersion % scope)
)

val hadoopClientExcludes =
  (moduleId: ModuleID) => moduleId.
    exclude("org.slf4j", "slf4j-api").
    exclude("javax.servlet", "servlet-api")

/*if it's a library the scope is "compile" since we want the transitive dependencies on the library
  otherwise we set up the scope to "provided" because those dependencies will be assembled in the "assembly"*/
lazy val assemblyDependenciesScope: String = if (isALibrary) "compile" else "provided"

lazy val hadoopDependenciesScope = if (isALibrary) "provided" else "compile"

libraryDependencies ++= Seq(
  sparkExcludes("org.apache.spark" %% "spark-core" % sparkVersion % "compile"),
  sparkExcludes("org.apache.spark" %% "spark-sql" % sparkVersion % "compile"),
  sparkExcludes("org.apache.spark" %% "spark-yarn" % sparkVersion % "compile"),
  sparkExcludes("org.apache.spark" %% "spark-mllib" % sparkVersion % "compile"),
  sparkExcludes("org.apache.spark" %% "spark-streaming" % sparkVersion % "compile"),
  hadoopClientExcludes("org.apache.hadoop" % "hadoop-yarn-api" % hadoopVersion % hadoopDependenciesScope),
  hadoopClientExcludes("org.apache.hadoop" % "hadoop-yarn-client" % hadoopVersion % hadoopDependenciesScope),
  hadoopClientExcludes("org.apache.hadoop" % "hadoop-yarn-common" % hadoopVersion % hadoopDependenciesScope),
  hadoopClientExcludes("org.apache.hadoop" % "hadoop-yarn-applications-distributedshell" % hadoopVersion % hadoopDependenciesScope),
  hadoopClientExcludes("org.apache.hadoop" % "hadoop-yarn-server-web-proxy" % hadoopVersion % hadoopDependenciesScope),
  hadoopClientExcludes("org.apache.hadoop" % "hadoop-client" % hadoopVersion % hadoopDependenciesScope)
) ++ assemblyDependencies(assemblyDependenciesScope)
