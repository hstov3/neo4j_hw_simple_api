# **neo4j_hw_simple_api** - Neo4J Consulting Engineer Homework

This is the deliverable for development of a simple API demonstrating access to Neo4j graph store.  

## API description

## Usage

```java
/**
 * The class that defines a Spark Web Framework route
 *
 */
public class TestController {

	public TestController() {
		get("/test", (request, response) ->  this.testMethod(request, response));
	}

	public String testMethod(Request request, Response response) {
		return "This works!";
	}

}
```

## Software Architecture

The webapp that serves the REST API is developed in Java using the [Spark Java](http://sparkjava.com/).  This provided a simple and lightweight framework for the application.  

Post body and responses are done using [GSON](https://github.com/google/gson) with POJO objects.  For the simple requirements of the server, only default serialization and deserialization was needed.  

## Server Deployment


## Requirements (reference)

The task is to develop a simple API that would process input data, communicate with Neo4J and return the results to the client.

The API - REST or GraphQL will support 2 operations:

1. **Create an Employee node**. This operation will take 2 params - a String and an int. The String value will populate the name property of the new Employee node, the int value will populate the emp_id value. Don't worry about data validation etc.

1. **Return all Employee nodes to the client**. Any format is fine.
	For the actual implementation use Java (Spring boot, Dropwizard, Spark java etc) or Javascript (NodeJS + Express etc). 

For your API - Neo4J communication use a Bolt driver (https://neo4j.com/docs/develo per-manual/current/drivers/).
 
When done, check your code into a github repo along with instructions on how to set up/use the API. 

To demonstrate API usage with any HTTP or GrapQL client (see bonus task #1 below) - please include instructions on how to use it in the README.md file in the github repo. 

* **Bonus task 1**: develop a simple UI that can be used directly from the web browser to drive the API - any stack will do, from plain HTML forms to JQuery to Angular/React/etc.

* **Bonus task 2**: deploy your app along with a Neo4J instance to an AWS EC2 instance  (or any other cloud) so that we can see it in action without going through the setup process.

