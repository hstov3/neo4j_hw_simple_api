/**
 * 
 */
package hs.neo4j_hw.simple_api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Statement;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Values;
import org.neo4j.driver.v1.summary.SummaryCounters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


/**
 * Service layer class to interact to underlying store, specifically Neo4j.  
 * 
 * @author Harry Stovall
 */
final class Neo4jEmployeeService implements AutoCloseable {
	
	private static final Logger LOG = LoggerFactory.getLogger(Neo4jEmployeeService.class);
    private final Driver neo4jDriver; 
	 
    public Neo4jEmployeeService(final Neo4jDriverConfig cfg) {
    	neo4jDriver = GraphDatabase.driver(cfg.getUriString(), 
    			AuthTokens.basic(cfg.getUser(), cfg.getPassword()));
    }
	
    /**
     * Find an Employee object corresponding to the given emp_id.  
     * 
     * Neo4J may throw ServiceUnavailableException which is RuntimeException which should be caught by calling method.
     * 
     * @param empl the given employee id
     * @return the resulting Employee object
     */
   Employee findById(int emp_id) {
    	Employee employeePOJO = null;
    	try (Session session = neo4jDriver.session()) {
    		// REFACTOR maybe cleaner to use RETURN collect(a) and deal with collection of nodes in the result.  
	    	StatementResult result = session.run("MATCH (a:Employee {emp_id = $emp_id}) RETURN a",
	    			Values.parameters("emp_id", emp_id));
            Record record = result.single();  // throws NoSuchRecordException
            Map<String,Object> nodeMap = record.asMap();
            LOG.info("found node: " + nodeMap.toString());
            // REFACTOR would be better to do this with JSON.  This will work for now.
            employeePOJO = new Employee(record.get("a.emp_id").asInt(), (String) record.get("a.name").asString());
    	}
    	return employeePOJO;
    }

    /**
     * Add the given Employee object to the graph data store.  
     * 
     * Neo4J may throw ServiceUnavailableException which is RuntimeException which should be caught by calling method.
     * 
     * @param empl
     * @return the given object
     */
    Employee add(final Employee empl) {
    	String propJson = new Gson().toJson(empl).toString();
    	try (Session session = neo4jDriver.session()) {
    		StatementResult stmtRslt = session.run("CREATE (a:Employee {propJson})", 
    				Values.parameters("propJson", propJson));
    		SummaryCounters counters = stmtRslt.summary().counters();
    		Statement stmt = stmtRslt.summary().statement();
    		LOG.info("Executed Cypher statement <" + stmt.text() + ">, nodesCreated=" + counters.nodesCreated() 
    		         + ", labelsAdded=" + counters.labelsAdded() + ", propertiesSet=" + counters.propertiesSet());
    	}
    	return empl;
    }

    /**
     * Find all employee objects in the graph store and result as a list of Employee POJO objects.
     * 
     * Neo4J may throw ServiceUnavailableException which is RuntimeException which should be caught by calling method.
     * 
     * @return
     */
    public List<Employee> findAll() {
    	
    	List<Employee> listResult = new ArrayList<>();
    	try (Session session = neo4jDriver.session()) {
    		// REFACTOR maybe cleaner to use RETURN collect(a) and deal with collection of nodes in the result.  
	    	StatementResult result = session.run( "MATCH (a:Employee) RETURN a" );
	        while (result.hasNext()) {
	            Record record = result.next();
	            Map<String,Object> nodeMap = record.asMap();
	            LOG.info("found node: " + nodeMap.toString());
	            // REFACTOR would be better to do this with JSON.  This will work for now.
	            Employee employeePOJO = new Employee(record.get("a.emp_id").asInt(), (String) record.get("a.name").asString());
	            listResult.add(employeePOJO);
	        }
    	}
    	return listResult;
    }
    
    public void close() {
    	try {
			neo4jDriver.close();
		} catch (Exception e) {
			LOG.warn("Unable to close neo4j driver", e);
		}
    }
	 
}
