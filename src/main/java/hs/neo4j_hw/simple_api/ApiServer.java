package hs.neo4j_hw.simple_api;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

class VersionObject {
	// TODO - handleVersionReq() - provide real implementation
	private final String version = "tbd";
	
	public final String getVersion() { 
		return version; 
	}
}


class ApiServer {

	private final EmployeeService employeeSvc;
	private static final Gson GSON = new Gson();
	
	ApiServer() {
		this.employeeSvc = new EmployeeService();
		registerRoutes();
	}
	
	private Object handleVersionReq(Request req, Response res) {
		// TODO - handleVersionReq() - provide real implementation
		res.type("application/json");
		return new VersionObject();
	}
	
	private Object handleEmployeeCreateReq(Request req, Response res) {
		res.type("application/json");
	    Employee empl = new Gson().fromJson(req.body(), Employee.class);
	    employeeSvc.add(empl);
		return empl;
	}
	
	private Object handleEmployeeListReq(Request req, Response res) {
		res.type("application/json");
		List<Employee> employeeList = this.employeeSvc.findAll();
		String gsonList = GSON.toJson(employeeList);
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
