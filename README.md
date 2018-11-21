# **neo4j_hw_simple_api** - Neo4J Consulting Engineer Homework

This is the deliverable for development of a simple API demonstrating access to Neo4j graph store.  

## API description

The REST api provided by the web program supports the following requests:

<table border="1">
 <thead>
  <tr>
   <th>HTTP Verb</th>
   <th>URI Path</th>
   <th>Post body</th>
   <th>Description</th>
   <th>Sample post body</th>
   <th>Sample JSON output</th>
  </tr>
 </thead>
 <tbody>
  <tr>
   <td>GET</td>
   <td>/api/version</td>
   <td>n/a</td>
   <td>Gets the version of the application.</td>
   <td></td>
   <td>{ "appVersion": "simple_api-0.0.1" }</td>
  </tr>
   <tr>
   <td>GET</td>
   <td>/api/employees</td>
   <td>n/a</td>
   <td>Gets the list of Employee nodes.</td>
   <td></td>
   <td>[{"emp_id":1,"name":"John"},{"emp_id":2,"name":"Jane"}]</td>
  </tr>
   <tr>
   <td>POST</td>
   <td>/api/employees</td>
   <td>JSON obect with with name value pairs for "name" and "emp_id"</td>
   <td>Inserts a node into the graph store with Employee label & properties specified by the JSON</td>
   <td>{"emp_id":3,"name":"Jim"}</td>
   <td>{"emp_id":3,"name":"Jim"}</td>
  </tr>
 </tbody>
</table>


## Usage

### Build and running locally

These instructions will provide a minimal development environment to run on a development computer.

1. Install the pre-requisite software if needed.  

    * If not already installed, install the latest version of the Java 8 JDK.  See [Oracle JDK Installers](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
    * If not already installed, install the latest version of Apache Maven.  See [Apache Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).  Validate using the `mvn --version` command to ensure Maven is using the the Java 8 JDK.
    * If not already installed, install git.  See [SCM Git Downloads](https://git-scm.com/downloads)
    * If not already installed, install Neo4j Desktop, [Neo4j Desktop Download](https://neo4j.com/download/), and follow the instructions after selecting "Download."
    * If not already installed, install Postman for http testing.  See [Download the App - Postman](https://git-scm.com/downloads) and follow the instructions at: [Postman installations and updates](https://learning.getpostman.com/docs/postman/launching_postman/installation_and_updates/)


2. If not done already, use **Neo4j Desktop** to create a project and a graph DB in that project.  Note the password that you assigned when creating the Graph DB.  

3. Use git to clone the repo here.  Change to the root directory where you want to create the directory with the code from this repo and run the following:

```cmd
$ git clone https://github.com/hstov3/neo4j_hw_simple_api.git
Cloning into 'neo4j_hw_simple_api'...
. . .
```

4.  At this point, one could open an IDE and set up a maven project to build and run the code within the IDE.  The rest of these instructions assume these steps are done from the command line.  Build the code:

```cmd
$ cd neo4j_hw_simple_api/
$ mvn package
[INFO] Scanning for projects...
[INFO]
[INFO] -----------------------< hs.neo4j_hw:simple_api >-----------------------
[INFO] Building simple_api 0.0.1
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  14.032 s
[INFO] Finished at: 2018-11-20T18:01:07-08:00
[INFO] ------------------------------------------------------------------------
```

    The process should end with a `BUILD SUCCESS`.  This builds the `simple_api-<version>.jar` file.  

5. For running from command line a maven assembly target is used to build a single jar file that contains all dependencies. Execute the following command:

```cmd
$ mvn assembly:single
...
[INFO] Building jar: C:\Users\Harry\eclipse-workspace\neo4j_hw_simple_api\target\simple_api-0.0.1-SNAPSHOT-jar-with-dependencies.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.957 s
[INFO] Finished at: 2018-11-20T20:22:13-08:00
[INFO] ------------------------------------------------------------------------
```

    The process should end with a `BUILD SUCCESS` and builds the `simple_api-<version>-jar-with-dependencies.jar` file.
  
6. Execute the following steps to configure the app to use the correct password.

```cmd
$ cd target
$ "$JAVA_HOME/bin/jar" xvf simple_api-<version>.jar neo4j-driver-server.properties  # replace <version> with the correct value.
$ vi neo4j-driver-server.properties    # or your favorite editor
  # modify the value of the neo4j.driver.server.passwd property to match your setting above.
```

7.  Run the web app as follows:

```cmd
$ java -cp "target;target/*" hs.neo4j_hw.simple_api.App
...
[Thread-1] INFO org.eclipse.jetty.server.Server - Started @1333ms
```

8.  Now the server is listening to requests.  Start the Postman app previously installed.  Import a pre-canned collection of http calls at [neo4j-hw-local-tests](https://www.getpostman.com/collections/159534c1be2cbfeb9421).  Results will be displayed in Postman.  A few INFO log entries will be displayed in the output of the program.  

## Server Deployment

To be provided. 

## Software Architecture Notes

The webapp that serves the REST API is developed in Java using the [Spark Java](http://sparkjava.com/).  This provided a simple and lightweight framework for the application.  

JSON Post body and responses are serialized and deserialized using [GSON](https://github.com/google/gson) with POJO objects.  For the simple requirements of the server, only the default serialization and deserialization was needed.  

The neo4j bolt driver as described in the requirements is used for access to the graph store.  

## Requirements (for reference)

The task is to develop a simple API that would process input data, communicate with Neo4J and return the results to the client.

The API - REST or GraphQL will support 2 operations:

1. **Create an Employee node**. This operation will take 2 params - a String and an int. The String value will populate the name property of the new Employee node, the int value will populate the emp_id value. Don't worry about data validation etc.

1. **Return all Employee nodes to the client**. Any format is fine.
	For the actual implementation use Java (Spring boot, Dropwizard, Spark java etc) or Javascript (NodeJS + Express etc). 


For your API - Neo4J communication use a Bolt driver (https://neo4j.com/docs/developer-manual/current/drivers/).
 
When done, check your code into a github repo along with instructions on how to set up/use the API. 

To demonstrate API usage with any HTTP or GrapQL client (see bonus task #1 below) - please include instructions on how to use it in the README.md file in the github repo. 

* **Bonus task 1**: develop a simple UI that can be used directly from the web browser to drive the API - any stack will do, from plain HTML forms to JQuery to Angular/React/etc.

* **Bonus task 2**: deploy your app along with a Neo4J instance to an AWS EC2 instance  (or any other cloud) so that we can see it in action without going through the setup process.

## Developer notes

A list of known issues that would be next steps to work are provided in TODO.txt.    

Development was done in the Eclipse IDE using the EGit (for git commands within the IDE) and m2e (for Maven-based projects) plugins. 

Any git commands in the above are provided as command-line commands.  There are various GUIs and IDE plug-ins that can be used for equivalent results. 
