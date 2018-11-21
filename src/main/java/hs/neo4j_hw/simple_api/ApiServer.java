package hs.neo4j_hw.simple_api;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;
import static spark.Spark.post;

class VersionObject {
	private final String appVersion;
	
	public VersionObject() {
		Package pkg = VersionObject.class.getPackage();
		appVersion = pkg.getImplementationTitle() + "-" + pkg.getImplementationVersion();
	}
	
	public final String getAppVersion() { 
		return appVersion;
	}
}


class ApiServer {

	private static final Logger LOG = LoggerFactory.getLogger(ApiServer.class);
	private static final Gson GSON = new Gson();
	private final Neo4jEmployeeService employeeSvc;
	
	ApiServer() {
		Neo4jEmployeeService initializedEmployeeSvc = null;
		try {
		    Neo4jDriverConfig neo4jCfg = new Neo4jDriverConfig();
		    initializedEmployeeSvc = new Neo4jEmployeeService(neo4jCfg);
		} catch (Exception e) {
			LOG.error("failed to initialize EmployeeService object", e);
			System.exit(-1);
		}
		this.employeeSvc = initializedEmployeeSvc;
		registerRoutes();
	}
	
	private Object handleVersionReq(Request req, Response res) {
		res.type("application/json");
		return new VersionObject();
	}
	
	private Object handleEmployeeCreateReq(Request req, Response res) {
		res.type("application/json");
	    Employee empl = null;
	    try {
	    	empl = new Gson().fromJson(req.body(), Employee.class);
	        employeeSvc.add(empl);
	        res.status(201); // 201 for Created
	    } catch (Exception e) {
	    	LOG.error("failure handling Create Employee request", e);
	    	res.status(500);
	    	res.body(e.getMessage());
	    	empl = null;
	    }
		return empl;
	}
	
	private Object handleEmployeeListReq(Request req, Response res) {
		res.type("application/json");
		String gsonList = "";
		try {
		    List<Employee> employeeList = this.employeeSvc.findAll();
		    gsonList = GSON.toJson(employeeList);
		} catch (Exception e) {
	    	LOG.error("failure handling Employee List request", e);
	    	res.status(500);
	    	res.body(e.getMessage());			
		}
		return gsonList;
	}
	
	private void registerRoutes() {
		get("/api/version", (req, res) -> this.handleVersionReq(req, res), 
				new JsonResponseTransformer());
		
    	get("/api/employees", (req, res) -> this.handleEmployeeListReq(req, res), 
				new JsonResponseTransformer());
    	
    	post("/api/employees", (req, res) -> this.handleEmployeeCreateReq(req, res), 
				new JsonResponseTransformer());
		
	}
	
}
