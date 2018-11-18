package hs.neo4j_hw.simple_api;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	get("/employee", (req, res) -> "Hello World");
    	
    	post("/employee", (req, res) -> {
    		return null;
    	});
    }
    

}
